package com.ichat.netty;

import com.ichat.enums.MsgActionEnum;
import com.ichat.service.UserService;
import com.ichat.utils.JsonUtils;
import com.ichat.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channle
    public static ChannelGroup users =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();

        Channel currentChannel = channelHandlerContext.channel();
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        if (dataContent != null){
            int action = dataContent.getAction();
            if(action == MsgActionEnum.CONNECT.type){
                String senderId = dataContent.getChatInfo().getSenderId();
                UserChannelRel.put(senderId,currentChannel);

                for (Channel user : users) {
                    System.out.println(user.id().asLongText());
                }
                UserChannelRel.output();

            }else if(action == MsgActionEnum.CHAT.type){
                ChatInfo chatInfo = dataContent.getChatInfo();
                String msg = chatInfo.getMsg();
                String senderId = chatInfo.getSenderId();
                String receiverId = chatInfo.getReceiverId();
                UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
                String msgId = userService.saveMsg(chatInfo);
                chatInfo.setMsgId(msgId);

                Channel receiverChannel = UserChannelRel.get(receiverId);
                if(receiverChannel == null){

                }else{
                    Channel channel = users.find(receiverChannel.id());
                    DataContent dataContent1 = new DataContent();
                    dataContent.setChatInfo(chatInfo);
                    if(channel != null){
                        receiverChannel.
                                writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
                    }
                }

            }else if(action == MsgActionEnum.SIGNED.type){
                UserService userService = (UserService) SpringUtil.getBean("userServiceImpl");
                String msgIdsStr = dataContent.getExtand();
                String[] msgIds = msgIdsStr.split(",");
                List<String> idsList = new ArrayList<>();
                for (String id : msgIds) {
                    if(StringUtils.isNotBlank(id)){
                        idsList.add(id);
                    }
                }
                System.out.println(idsList);
                if(idsList != null && !idsList.isEmpty() && idsList.size() > 0){
                    userService.updateMsgSigned(idsList);
                }

            }else if(action == MsgActionEnum.KEEPALIVE.type){
                System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
            }
        }

    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}

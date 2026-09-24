package com.example.springcloudtalk.websocket;

public class ChatMessage {
    private String to;      // 目标sid，为空则广播（不含自己）
    private String content; // 消息内容

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
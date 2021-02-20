package net.micropower.weixin.process;

import javax.servlet.http.HttpServletRequest;

import net.zhys.weixin.bean.InMessage;
import net.zhys.weixin.bean.OutMessage;

/**
 * 默认处理委托
 * 
 * @author lihui 2013-8-13
 * 
 */
public abstract class DefaultMessageProcessingHandlerImpl implements
		MessageProcessingHandler
{

	private OutMessage allType(InMessage msg)
	{
		OutMessage out = new OutMessage();
		out.setContent("您的消息已经收到！");
		return out;
	}

	@Override
	public OutMessage textTypeMsg(InMessage msg,HttpServletRequest request)
	{
		return allType(msg);
	}

	@Override
	public OutMessage locationTypeMsg(InMessage msg,HttpServletRequest request)
	{
		return allType(msg);
	}

	@Override
	public OutMessage imageTypeMsg(InMessage msg)
	{
		return allType(msg);
	}

	@Override
	public OutMessage linkTypeMsg(InMessage msg)
	{
		return allType(msg);
	}

	@Override
	public OutMessage eventTypeMsg(InMessage msg,HttpServletRequest request)
	{
		return allType(msg);
	}

}

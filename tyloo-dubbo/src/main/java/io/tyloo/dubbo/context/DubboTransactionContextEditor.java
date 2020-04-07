package io.tyloo.dubbo.context;

import com.alibaba.fastjson.JSON;
import io.tyloo.api.TransactionContext;
import io.tyloo.api.TransactionContextEditor;
import io.tyloo.dubbo.constants.TransactionContextConstants;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.RpcContext;

import java.lang.reflect.Method;

/*
 * Dubbo ���������ı༭
 *
 * ����ײ������ʹ�õ���dubbo����������TransactionContextEditorΪDubboTransactionContextEditor.class��ʹ��dubbo��ʽ���η�ʽ����
 * ͨ�� Dubbo ����ʽ���εķ�ʽ�������� Dubbo Service �ӿ������� TransactionContext �������Խӿڲ���һ��������
 * tyloo ͨ�� Dubbo Proxy �Ļ��ƣ�ʵ�� `@Tyloo` �����Զ�����
 *
 * @Author:Zh1Cheung zh1cheunglq@gmail.com
 * @Date: 15:32 2019/7/17
 *
 */

public class DubboTransactionContextEditor implements TransactionContextEditor {
    @Override
    public TransactionContext get(Object target, Method method, Object[] args) {

        String context = RpcContext.getContext().getAttachment(TransactionContextConstants.TRANSACTION_CONTEXT);

        if (StringUtils.isNotEmpty(context)) {
            return JSON.parseObject(context, TransactionContext.class);
        }

        return null;
    }

    @Override
    public void set(TransactionContext transactionContext, Object target, Method method, Object[] args) {

        RpcContext.getContext().setAttachment(TransactionContextConstants.TRANSACTION_CONTEXT, JSON.toJSONString(transactionContext));
    }
}
package com.fintech.rabbitmq.util;

import com.fintech.common.constants.CommonConstants;
import com.fintech.rabbitmq.service.AsAbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @ClassName HttpContentUtil
 * @Date 2020/3/25 15:08
 * @Auther wangyongyong
 * @Version 1.0
 * @Description http 请求上下文工具类
 */
@Component
public class HttpContentUtil
{
    private static final Logger logger = LoggerFactory.getLogger(HttpContentUtil.class);

    private HttpContentUtil()
    {

    }

    private static HttpContentUtil contentUtil;

    @Autowired(required = false)
    private HttpServletRequest request;

    @PostConstruct
    public void init()
    {
        HttpContentUtil.contentUtil = this;
    }

    /**
     * 获取产品 id
     *
     * @return
     */
    public static String getProductId()
    {
        Integer productId = AsAbstractService.getProductId();
        if (productId != null)
        {
            return productId.toString();
        }
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getHeader(CommonConstants.HEAD_PRODUCT_ID);
    }

    /**
     * 获取产品 id
     *
     * @return
     */
    public static Integer getIntProductId()
    {
        Integer productId = AsAbstractService.getProductId();
        if (productId != null)
        {
            return productId;
        }
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getIntHeader(CommonConstants.HEAD_PRODUCT_ID);
    }

    /**
     * 获取 用户 Id
     *
     * @return
     */
    public static Integer getUserId()
    {
        Integer userId = AsAbstractService.getUserId();
        if (userId != null)
        {
            return userId;
        }
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getIntHeader(CommonConstants.HEAD_USERID);
    }

    /**
     * 获取企业 ID
     *
     * @return
     */
    public static Integer getCpyId()
    {
        Integer cpyId = AsAbstractService.getCpyId();
        if (cpyId != null)
        {
            return cpyId;
        }
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getIntHeader(CommonConstants.HEAD_CPYID);
    }

    /**
     * 获取用户类型
     *
     * @return
     */
    public static String getUserType()
    {
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getHeader(CommonConstants.HEAF_USER_TYPE);
    }

    /**
     * 获取数据权限类型
     *
     * @return
     */
    public static Integer getDtType()
    {
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getIntHeader(CommonConstants.DT_TYPE);
    }

    /**
     * 获取操作码
     *
     * @return
     */
    public static String getOpCode()
    {
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getHeader(CommonConstants.OP_CODE);
    }

    /**
     * 获取下一步操作码
     *
     * @return
     */
    public static String getOpNCode()
    {
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return null;
        }
        return contentUtil.request.getHeader(CommonConstants.OP_NCODE);
    }

    /**
     * 打印 http header 头部信息
     */
    public static void printHeaders()
    {
        if (RequestContextHolder.getRequestAttributes() == null || null == contentUtil.request)
        {
            return;
        }
        Enumeration<String> enumeration = contentUtil.request.getHeaderNames();
        while (enumeration.hasMoreElements())
        {
            String header = enumeration.nextElement();
            logger.info("Header【" + header + "】 ====>【" + contentUtil.request.getHeader(header) + "】");
        }
    }

    /**
     * 打印 http body 信息
     */
    public static void printBody() throws IOException
    {
        if (RequestContextHolder.getRequestAttributes() == null || contentUtil.request == null)
        {
            return;
        }
        logger.info("Body ====>【" + contentUtil.request.getInputStream() + "】");
    }

}

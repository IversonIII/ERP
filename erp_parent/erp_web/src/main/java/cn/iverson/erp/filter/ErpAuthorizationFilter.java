package cn.iverson.erp.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Iverson on 2017/7/30.
 */
public class ErpAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String [] perms = (String[]) o;
        boolean isPermitted = false;
        if(perms != null && perms.length > 0){
            //只要有一个，就让它通过
            for(String perm : perms) {
                if (subject.isPermitted(perm)) {
                    isPermitted = true;
                    break;
                }
            }
        } else {
            isPermitted = true;
        }
        return isPermitted;
    }
}

package com.hicon.frame.apiversion;


import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    private static final Logger logger = Logger.getLogger(ApiVersionCondition.class);

    private final static Pattern         VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+).*");
    /**
     * 版本注解
     */
    private              ApiVersionState apiVersionState;

    public ApiVersionState getApiVersionState() {
        return apiVersionState;
    }

    public void setApiVersionState(ApiVersionState apiVersionState) {
        this.apiVersionState = apiVersionState;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiVersionCondition that = (ApiVersionCondition) o;
        return Objects.equals(apiVersionState, that.apiVersionState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiVersionState);
    }

    public ApiVersionCondition(ApiVersionState apiVersionState) {
        this.apiVersionState = apiVersionState;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        return new ApiVersionCondition(apiVersionCondition.getApiVersionState());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        logger.info("ApiVersionCondition-getMatchingCondition... ...");

        Matcher m = VERSION_PREFIX_PATTERN.matcher(httpServletRequest.getRequestURI());
        if (m.find()) {
            int version = Integer.parseInt(m.group(1));
            if (version >= this.apiVersionState.getVersion()) {
                if (this.apiVersionState.isDiscard() && this.apiVersionState.getVersion() == version) {
                    throw new ApiVersionDiscardException("当前版本已停用，请升级到最新版本。");
                }
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        return apiVersionCondition.getApiVersionState().getVersion() - this.apiVersionState.getVersion();
    }
}

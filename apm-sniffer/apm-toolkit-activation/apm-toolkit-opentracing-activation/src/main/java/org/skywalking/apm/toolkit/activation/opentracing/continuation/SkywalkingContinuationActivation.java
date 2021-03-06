package org.skywalking.apm.toolkit.activation.opentracing.continuation;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.skywalking.apm.agent.core.plugin.match.ClassMatch;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * {@link SkywalkingContinuationActivation} defines two interceptors to enhance the methods and constructor in class
 * <code>org.skywalking.apm.toolkit.opentracing.SkywalkingContinuation</code>.
 *
 * 1. The <code>org.skywalking.apm.toolkit.activation.opentracing.continuation.ConstructorInterceptor</code>
 * interceptor enhance the constructor.
 *
 * 2. The <code>org.skywalking.apm.toolkit.activation.opentracing.continuation.ActivateInterceptor</code>
 * interceptor enhance the <code>activate</code>.
 */
public class SkywalkingContinuationActivation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "org.skywalking.apm.toolkit.opentracing.SkywalkingContinuation";
    private static final String CONSTRUCTOR_INTERCEPTOR = "org.skywalking.apm.toolkit.activation.opentracing.continuation.ConstructorInterceptor";
    private static final String ACTIVATE_METHOD_INTERCEPTOR = "org.skywalking.apm.toolkit.activation.opentracing.continuation.ActivateInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
    }

    @Override
    protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[] {
            new ConstructorInterceptPoint() {
                @Override public ElementMatcher<MethodDescription> getConstructorMatcher() {
                    return any();
                }

                @Override public String getConstructorInterceptor() {
                    return CONSTRUCTOR_INTERCEPTOR;
                }
            }
        };
    }

    @Override
    protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
            new InstanceMethodsInterceptPoint() {
                @Override public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return named("activate");
                }

                @Override public String getMethodsInterceptor() {
                    return ACTIVATE_METHOD_INTERCEPTOR;
                }

                @Override public boolean isOverrideArgs() {
                    return false;
                }
            }
        };
    }
}

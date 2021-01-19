package com.yl.seckill.configuration;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

/**
 * @author Administrator
 */
public class ArchitectureForce {
    private static final String DEFAULT_CUZ = "ArchitectureForce";
    private static final List<ArchRule> forceRules = new LinkedList<>();

    public static ArchRule loggerShouldPrivateStaticFinal() {
        return fields()
                .that().haveRawType(Logger.class)
                .should().bePrivate()
                .andShould().beStatic()
                .andShould().beFinal()
                .because(DEFAULT_CUZ);
    }

    public static ArchRule invalidClassName() {
        return noClasses()
                .should().haveSimpleNameEndingWith("Annotation")
                .orShould().haveSimpleNameEndingWith("Class")
                .orShould().haveSimpleNameEndingWith("Interface")
                .because(DEFAULT_CUZ);
    }

    public static Architectures.LayeredArchitecture controllerRule() {
        //controller定义在..controller..包下,且不能被其它模块访问
        return layeredArchitecture()
                .layer("Controller").definedBy("..controller..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer();
    }

    static {
        forceRules.add(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS);
        forceRules.add(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS);
        forceRules.add(invalidClassName());
        forceRules.add(loggerShouldPrivateStaticFinal());
        forceRules.add(controllerRule());
    }

    public static void runAllRule(JavaClasses classes) {
        forceRules.forEach(ru -> ru.check(classes));
    }
}
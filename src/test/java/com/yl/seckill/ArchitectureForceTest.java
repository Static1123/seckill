package com.yl.seckill;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.yl.seckill.configuration.ArchitectureForce;
import org.junit.jupiter.api.Test;

/**
 * @author Administrator
 */
public class ArchitectureForceTest {
    @Test
    public void test() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.yl.seckill");
        ArchitectureForce.runAllRule(classes);
    }
}
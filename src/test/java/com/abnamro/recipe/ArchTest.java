package com.abnamro.recipe;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {
    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        var importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.abnamro.recipe");

        noClasses()
                .that()
                .resideInAnyPackage("com.abnamro.recipe.service..")
                .or()
                .resideInAnyPackage("com.abnamro.recipe.repository..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..com.abnamro.recipe.controller..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }

}

package com.poc.drools

import org.kie.api.KieServices
import org.kie.api.builder.KieBuilder
import org.kie.api.builder.KieFileSystem
import org.kie.api.builder.KieModule
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory.newClassPathResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DroolsConfiguration {
    private val kieServices: KieServices = KieServices.Factory.get()

    @Bean
    fun getKieContainer(): KieContainer? {
        val kieFileSystem: KieFileSystem = kieServices.newKieFileSystem()
        kieFileSystem.write(newClassPathResource("DroolsPoc.drl"))
        val kb: KieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kb.buildAll()
        val kieModule: KieModule = kb.getKieModule()
        return kieServices.newKieContainer(kieModule.getReleaseId())
    }
}
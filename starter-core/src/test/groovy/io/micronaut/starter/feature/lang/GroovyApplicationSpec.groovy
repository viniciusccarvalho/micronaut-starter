package io.micronaut.starter.feature.lang

import io.micronaut.starter.BeanContextSpec
import io.micronaut.starter.application.ApplicationType
import io.micronaut.starter.fixture.CommandOutputFixture
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import io.micronaut.starter.options.Options
import io.micronaut.starter.options.TestFramework
import io.micronaut.starter.util.VersionInfo
import spock.lang.Unroll

class GroovyApplicationSpec extends BeanContextSpec implements CommandOutputFixture {
    void 'Application file is generated for a default application type with gradle and referenced in build.gradle mainClassName for language: groovy'() {
        given:
        def policy = VersionInfo.isMicronautSnapshot() ? "enforcedPlatform" : "platform"
        def testPolicy = "enforcedPlatform"

        when:
        def output = generate(
                ApplicationType.DEFAULT,
                new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE),
                []
        )

        then:
        output.containsKey("src/main/groovy/example/micronaut/Application.${Language.GROOVY.extension}".toString())

        when:
        def buildGradle = output['build.gradle']

        then:
        buildGradle.contains('mainClassName = "example.micronaut.Application"')

        and: 'implementation compileOnly testImplementation use the same policy'
        buildGradle.contains("compileOnly($policy(\"io.micronaut:micronaut-bom:\$micronautVersion\")")
        buildGradle.contains("implementation($policy(\"io.micronaut:micronaut-bom:\$micronautVersion\")")
        buildGradle.contains("testImplementation($testPolicy(\"io.micronaut:micronaut-bom:\$micronautVersion\")")
    }

    @Unroll
    void "test generated Groovy application for build tool - #build"() {
        given:
        def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, build))

        expect:
        output[build.buildFileName].contains(dependency)

        where:
        build            | dependency
        BuildTool.MAVEN  | """<dependency>
      <groupId>io.micronaut</groupId>
      <artifactId>micronaut-inject-groovy</artifactId>
      <scope>provided</scope>
    </dependency>"""
        BuildTool.GRADLE | 'compileOnly("io.micronaut:micronaut-inject-groovy")'
    }
}

package org.darkSolace.muse

import org.darkSolace.muse.testUtil.TestBase
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ElysianMuseApplicationTests : TestBase() {
    @Autowired
    lateinit var em: EntityManager

    /**
     * Checks if Spring Boot is starting without issues
     */
    @Test
    @Order(1)
    fun contextLoads() {
        //just checking
    }

    /**
     * Checks if the testcontainer is loading properly without issues
     */
    @Test
    @Order(2)
    fun testContainerLoads() {
        val result = em.createNativeQuery("SELECT 1 FROM pg_tables").singleResult
        Assertions.assertTrue(result is Int)
        Assertions.assertEquals(1, (result as Int))
    }

    @Test
    @Order(3)
    fun testSecretCheck() {
        //should not fail as secret is set long enough
        secretCheck()
        //if secretCheck doesn't exit pass
    }
}

package works.nobushi.beacon.epoch

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EpochBeaconIntegrationTests {

    private lateinit var requester: RSocketRequester

    @MockkBean
    private lateinit var service: EpochBeaconService

    @BeforeEach
    fun setUp(
        @Autowired builder: RSocketRequester.Builder,
        @LocalServerPort port: Int
    ) {
        requester = builder.connectWebSocket(URI.create("http://localhost:$port/rsocket")).block()!!
    }

    @AfterEach
    fun tearDown() {
        requester.rsocket().dispose()
    }

    @Test
    fun `Assert epoch`() {
        every {
            service.epoch()
        } answers {
            Flux.range(0, 10).map { Epoch(it.toLong() * 1000) }
        }

        val result = requester.route("epoch").retrieveFlux<Epoch>().take(3)
        StepVerifier.create(result)
            .expectNext(Epoch(0))
            .expectNext(Epoch(1000))
            .expectNext(Epoch(2000))
            .verifyComplete()
    }
}

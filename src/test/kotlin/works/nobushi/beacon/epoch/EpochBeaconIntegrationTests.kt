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
import reactor.test.StepVerifier
import java.net.URI
import java.time.Clock
import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EpochBeaconIntegrationTests {

    private lateinit var requester: RSocketRequester

    @MockkBean
    private lateinit var clock: Clock

    @BeforeEach
    fun setUp(
        @Autowired builder: RSocketRequester.Builder,
        @LocalServerPort port: Int
    ) {
        requester = builder.websocket(URI.create("http://localhost:$port/rsocket"))
    }

    @AfterEach
    fun tearDown() {
        requester.dispose()
    }

    @Test
    fun `Assert epoch`() {
        var time = 0L
        every {
            clock.instant()
        } answers {
            val instant = Instant.ofEpochMilli(time)
            time += 1000
            instant
        }

        val result = requester.route("epoch").retrieveFlux<Epoch>().take(3)
        StepVerifier.create(result)
            .expectNext(Epoch(0))
            .expectNext(Epoch(1000))
            .expectNext(Epoch(2000))
            .verifyComplete()
    }
}

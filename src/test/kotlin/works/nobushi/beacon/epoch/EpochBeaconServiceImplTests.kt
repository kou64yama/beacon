package works.nobushi.beacon.epoch

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier
import java.time.Clock
import java.time.Instant

@SpringBootTest
class EpochBeaconServiceImplTests(
    @Autowired private val service: EpochBeaconServiceImpl
) {

    @MockkBean
    private lateinit var clock: Clock

    @Test
    fun epoch() {
        var i = 0L
        every {
            clock.instant()
        } answers {
            Instant.ofEpochSecond(i++)
        }

        StepVerifier.create(service.epoch().take(3))
            .expectNext(Epoch(0))
            .expectNext(Epoch(1000))
            .expectNext(Epoch(2000))
            .verifyComplete()
    }
}

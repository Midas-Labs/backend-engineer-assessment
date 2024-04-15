import com.midas.app.MidasApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
    classes = MidasApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class IntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  //  @Container
  //  public static PostgreSQLContainer<?> postgreSQLContainer =
  //      new PostgreSQLContainer<>("postgres:13")
  //          .withDatabaseName("mydatabase")
  //          .withDatabaseName("myuser")
  //          .withPassword("secret");
  //
  //  @Container
  //  private static final GenericContainer<?> temporalServerContainer =
  //      new GenericContainer<>("temporalio/auto-setup:latest")
  //          .withCreateContainerCmdModifier(
  //              cmd -> cmd.getHostConfig().withMemory(2147483648L)) // 2 GB
  //          .withExposedPorts(7233);

  //  @Test
  //  public void testApi() {
  //    String response =
  //        this.restTemplate.getForObject(
  //            "http://localhost:8080" + port + "/api-endpoint", String.class);
  //    assertEquals("Expected response", response);
  //  }
}

package todomvc;

import mbt.todomvc.TodoMvcModelImpl;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.java.test.TestBuilder;
import org.testng.annotations.Test;

import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

import java.io.IOException;
import java.nio.file.Path;

public class SampleTest {

    private final static Path MODEL_PATH = Path.of("mbt/todomvc/models/TodoMvcModel.json");

    @Test
    public void test1() {
        var result = new TestBuilder()
                .addContext(
                        new TodoMvcModelImpl().setNextElement(new Vertex().setName("v_EmptyList")),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100))
                ).execute();
        System.out.println(result);
    }

    @Test
    public void test2() throws IOException {
        Result result = new TestExecutor(TodoMvcModelImpl.class)
                .execute();
        System.out.println(result);
    }
}

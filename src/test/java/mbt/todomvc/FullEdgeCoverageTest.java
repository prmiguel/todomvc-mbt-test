package mbt.todomvc;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.java.test.TestBuilder;
import org.testng.annotations.Test;

import java.nio.file.Path;

import static mbt.todomvc.utils.ResultUtils.generateJsonResult;

public class FullEdgeCoverageTest {

    private final static Path MODEL_PATH = Path.of("mbt/todomvc/models/TodoMvcModel.json");

    @Test
    public void simulation() {
        var modelUnderTest = new TodoMvcModelImpl();
        var result = new TestBuilder()
                .addContext(
                        modelUnderTest.setNextElement(new Vertex().setName("v_EmptyList")),
                        MODEL_PATH,
                        new RandomPath(new EdgeCoverage(100))
                ).execute();
        generateJsonResult(MODEL_PATH, modelUnderTest.getSequence(), result, "FullEdgeCoverageTest");
    }
}

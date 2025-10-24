package mbt.todomvc;

import com.codeborne.selenide.Selenide;
import mbt.todomvc.models.TodoMvcCoreModel;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeElement;
import org.graphwalker.java.annotation.BeforeExecution;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static mbt.todomvc.utils.ImageUtils.optimizeImage;

public class TodoMvcModelImpl extends ExecutionContext implements TodoMvcCoreModel {

    private static final Logger logger = LoggerFactory.getLogger(TodoMvcModelImpl.class);
    record Sequence(String type, String id, String image) {}
    private static final List<Sequence> sequence = new ArrayList<>();

    public List<Sequence> getSequence() {
        return sequence;
    }

    @BeforeExecution
    public void s_openApp() {
        open("/");
    }

    @AfterExecution
    public void t_collectData() {
    }

    @BeforeElement
    public void beforeElement() {
        String elementName = getCurrentElement().getName();
        if (elementName.startsWith("e_")) {
            sequence.add(new Sequence("edge", getCurrentElement().getId(), null));
            return;
        }
        String screenshotAsBase64 = Selenide.screenshot(OutputType.BASE64);
        screenshotAsBase64 = optimizeImage(Objects.requireNonNull(screenshotAsBase64));
        sequence.add(new Sequence("vertex", getCurrentElement().getId(), screenshotAsBase64));
    }

    @Override
    public void e_uncompleteTodo() {
        $(".todo-list li", 0).find(".toggle").click();
    }

    @Override
    public void e_clearCompletedTodos() {
        $(".clear-completed").should(visible.because("'Clear completed' not visible")).hover().click();
    }

    @Override
    public void e_seeAllTodos() {
        $(".filters a", 0).shouldHave(text("All")).click();
    }

    @Override
    public void e_seeActiveTodos() {
        $(".filters a", 1).shouldHave(text("Active")).click();
    }

    @Override
    public void v_CompletedTodos() {
        $(".filters a", 2).shouldHave(text("Completed"), cssClass("selected"));
    }

    @Override
    public void v_ActiveTodos() {
        $(".filters a", 1).shouldHave(text("Active"), cssClass("selected"));
    }

    @Override
    public void v_EmptyList() {
        $(".todo-count").should(disappear);
    }

    @Override
    public void e_addTodo() {
        $(".new-todo").setValue("make coffee").pressEnter();
        $(".filters a", 0).shouldHave(text("All")).click();
    }

    @Override
    public void e_completeTodo() {
        $(".todo-list li", 0).find(".toggle").click();
    }

    @Override
    public void e_addAnotherTodo() {
        $(".new-todo").setValue("make coffee" + new Random().nextInt(100000, 999999)).pressEnter();
    }

    @Override
    public void e_seeCompletedTodos() {
        $(".filters a", 2).shouldHave(text("Completed")).click();
    }

    @Override
    public void v_AllTodos() {
        $(".filters a", 0).shouldHave(text("All"), cssClass("selected"));
        $$(".todo-list .view").shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Override
    public void e_deleteUniqueTodo() {
        $$(".todo-list .view").shouldHave(size(1));
        $(".todo-list li", 0).hover().find(".destroy").click();
    }

    @Override
    public void e_updateExistingTodo() {
        $$(".todo-list .view").shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Override
    public void e_deleteCompletedTodo() {
        $(".todo-list .completed", 0).hover().find(".destroy").click();
    }

    @Override
    public void e_deleteActiveTodo() {
        $(".todo-list [class=\"\"]", 0).hover().find(".destroy").click();
    }
}

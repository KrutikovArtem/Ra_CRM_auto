package web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class NewContactCreator {
    SelenideElement chek = $x("//span[text()='Редактировать']");


    public void chek(){
        chek.shouldBe(Condition.enabled);
    }
}

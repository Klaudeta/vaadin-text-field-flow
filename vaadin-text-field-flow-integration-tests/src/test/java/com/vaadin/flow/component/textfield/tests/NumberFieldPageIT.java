/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.textfield.tests;

import static org.junit.Assert.assertFalse;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.testbench.NumberFieldElement;
import com.vaadin.flow.testutil.AbstractComponentIT;
import com.vaadin.flow.testutil.TestPath;

/**
 * Integration tests for {@link NumberField}.
 */
@TestPath("number-field-test")
public class NumberFieldPageIT extends AbstractComponentIT {

    @Before
    public void init() {
        open();
    }

    @Test
    public void assertReadOnly() {
        NumberFieldElement numberField = $(NumberFieldElement.class).first();
        WebElement messageDiv = $("div").id("message");
        numberField.setValue("123.0");

        assertFalse(numberField.hasAttribute("readonly"));

        WebElement readOnlyButton = findElement(By.id("read-only"));
        readOnlyButton.click();

        numberField.setValue("456");
        Assert.assertEquals("123.0", numberField.getValue());
        Assert.assertEquals("Old value: 'null'. New value: '123.0'.", messageDiv.getText());

        numberField.setProperty("readonly", "");
        numberField.setValue("789");
        Assert.assertEquals("123.0", numberField.getValue());
        Assert.assertEquals("Old value: 'null'. New value: '123.0'.", messageDiv.getText());

        readOnlyButton.click();
        numberField.setValue("987");
        Assert.assertEquals("Old value: '123.0'. New value: '987.0'.", messageDiv.getText());
    }

    @Test
    public void assertEnabled() {
        NumberFieldElement numberField = $(NumberFieldElement.class).first();
        WebElement messageDiv = $("div").id("message");
        numberField.setValue("123.0");

        assertFalse(numberField.hasAttribute("disabled"));
        WebElement disableEnableButton = findElement(By.id("disabled"));
        disableEnableButton.click();

        numberField.setValue("456");
        Assert.assertEquals("Old value: 'null'. New value: '123.0'.", messageDiv.getText());

        numberField.setProperty("disabled", "");
        numberField.setValue("789");
        Assert.assertEquals("Old value: 'null'. New value: '123.0'.", messageDiv.getText());
        
        disableEnableButton.click();
        numberField.setValue("987");
        Assert.assertEquals("Old value: '123.0'. New value: '987.0'.", messageDiv.getText());
    }

    @Test
    public void assertRequired() {
        NumberFieldElement numberField = $(NumberFieldElement.class).first();

        assertFalse(numberField.hasAttribute("required"));

        WebElement button = findElement(By.id("required"));
        button.click();
        waitUntil(attributeToBe(numberField, "required", "true"));

        button.click();
        waitUntil(attributeToBe(numberField, "required", ""));
    }

    @Test
    public void assertClearValue() {
        NumberFieldElement field = $(NumberFieldElement.class).id("clear-number-field");

        WebElement input = field.$("input").first();
        input.sendKeys("300");
        blur();

        WebElement clearButton = getInShadowRoot(field, By.cssSelector("[part~='clear-button']"));
        clearButton.click();

        String value = findElement(By.id("clear-message")).getText();
        Assert.assertEquals("Old value: '300.0'. New value: 'null'.", value);
    }

    @Test
    public void assertStepValue() {
        WebElement field = findElement(By.id("step-number-field"));

        WebElement increaseButton = getInShadowRoot(field, By.cssSelector("[part~='increase-button']"));
        increaseButton.click();

        String value = findElement(By.id("step-message")).getText();
        Assert.assertEquals("Old value: 'null'. New value: '0.5'.", value);
    }

    @Test
    public void assertValueChange() {
        NumberFieldElement field = $(NumberFieldElement.class).id("clear-number-field");
        field.setValue("123.0");
        String message = $("div").id("clear-message").getText();
        Assert.assertEquals("Old value: 'null'. New value: '123.0'.", message);
    }

}

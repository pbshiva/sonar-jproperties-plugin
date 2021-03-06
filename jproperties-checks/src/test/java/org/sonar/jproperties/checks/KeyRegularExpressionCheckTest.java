/*
 * SonarQube Java Properties Plugin
 * Copyright (C) 2016 David RACODON
 * david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.jproperties.checks;

import org.junit.Test;
import org.sonar.jproperties.JavaPropertiesAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

public class KeyRegularExpressionCheckTest {

  private final String PATH = "src/test/resources/checks/keyRegularExpression.properties";
  private KeyRegularExpressionCheck check = new KeyRegularExpressionCheck();

  @Test
  public void should_match_some_keys_and_raise_issues() {
    String message = "Find out keys starting with 'mykey'";
    check.regularExpression = "^mykey.*";
    check.message = message;
    SourceFile file = JavaPropertiesAstScanner.scanSingleFile(new File(PATH), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(1).withMessage(message)
      .next().atLine(2).withMessage(message)
      .noMore();
  }

  @Test
  public void should_not_match_any_keys_and_not_raise_issues() {
    String message = "Find out keys starting with 'blabla'";
    check.regularExpression = "^blabla.*";
    check.message = message;
    SourceFile file = JavaPropertiesAstScanner.scanSingleFile(new File(PATH), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test(expected = IllegalStateException.class)
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_regular_expression_is_not_valid() {
    check.regularExpression = "(";
    check.message = "blabla";
    JavaPropertiesAstScanner.scanSingleFile(new File(PATH), check);
  }

}

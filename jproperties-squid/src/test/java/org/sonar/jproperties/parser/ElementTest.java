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
package org.sonar.jproperties.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class ElementTest extends TestBase {

  private LexerlessGrammar b = JavaPropertiesGrammar.createGrammar();

  @Test
  public void should_match_elements() {
    assertThat(b.rule(JavaPropertiesGrammar.ELEMENT))
      .matches("abc")
      .matches("abc.def")
      .matches("abc=def")
      .matches("abc=:def")
      .matches(" abc")
      .matches("  abc")
      .matches("\\u8ACB\\u7A0D")
      .matches("\\u8ACB")
      .matches("\\")
      .matches(multiLineUnixLineEnding("\\", "\\", "  abc \\ "))
      .matches(multiLineWindowsLineEnding("\\", "\\", "  abc \\ "))
      .matches(multiLineUnixLineEnding("\\", "\\", "  abc \\ "))
      .matches(multiLineWindowsLineEnding("\\", "\\", "  abc \\ "));
  }

  @Test
  public void should_not_match_elements() {
    assertThat(b.rule(JavaPropertiesGrammar.ELEMENT))
      .notMatches("")
      .notMatches(" ")
      .notMatches("  ");
  }

}

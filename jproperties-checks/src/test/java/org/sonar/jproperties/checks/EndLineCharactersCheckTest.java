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

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.jproperties.JavaPropertiesAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class EndLineCharactersCheckTest {

  private EndLineCharactersCheck check = new EndLineCharactersCheck();
  private SourceFile file;

  @Test
  public void should_find_only_crlf_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test
  public void should_find_only_cr_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("CR");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test
  public void should_find_only_lf_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("LF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test
  public void crlf_should_find_lf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'CRLF' in this file.")
      .noMore();
  }

  @Test
  public void crlf_should_find_cr_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'CRLF' in this file.")
      .noMore();
  }

  @Test
  public void cr_should_find_crlf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CR");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'CR' in this file.")
      .noMore();
  }

  @Test
  public void cr_should_find_lf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CR");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'CR' in this file.")
      .noMore();
  }

  @Test
  public void lf_should_find_crlf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("LF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r\n"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'LF' in this file.")
      .noMore();
  }

  @Test
  public void lf_should_find_cr_and_raise_issues() throws Exception {
    check.setEndLineCharacters("LF");
    file = JavaPropertiesAstScanner.scanSingleFile(getTestFileWithProperEndLineCharacters("\r"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Set all end-line characters to 'LF' in this file.")
      .noMore();
  }

  private File getTestFileWithProperEndLineCharacters(String endLineCharacter) throws Exception {
    TemporaryFolder temporaryFolder = new TemporaryFolder();
    File testFile = temporaryFolder.newFile();
    Files.write(
      Files.toString(new File("src/test/resources/checks/endLineCharacters.properties"), Charsets.ISO_8859_1)
        .replaceAll("\\r\\n", "\n")
        .replaceAll("\\r", "\n")
        .replaceAll("\\n", endLineCharacter),
      testFile, Charsets.ISO_8859_1);
    return testFile;
  }

}

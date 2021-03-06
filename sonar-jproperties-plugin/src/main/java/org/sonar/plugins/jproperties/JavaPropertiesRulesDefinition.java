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
package org.sonar.plugins.jproperties;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.jproperties.checks.CheckList;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

public class JavaPropertiesRulesDefinition implements RulesDefinition {

  @Override
  public void define(Context context) {
    NewRepository repository = context
      .createRepository(JavaProperties.KEY, JavaProperties.KEY)
      .setName(CheckList.REPOSITORY_NAME);

    AnnotationBasedRulesDefinition.load(repository, JavaProperties.KEY, CheckList.getChecks());

    repository.done();
  }

}

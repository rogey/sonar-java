/*
 * SonarQube Java
 * Copyright (C) 2012-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.checks;

import java.util.Collections;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "S2111")
public class BigDecimalDoubleConstructorCheck extends IssuableSubscriptionVisitor {

  private static final MethodMatchers BIG_DECIMAL_DOUBLE_FLOAT =
    MethodMatchers.create().ofTypes("java.math.BigDecimal")
      .constructor()
      .addParametersMatcher("double")
      .addParametersMatcher("float")
      .addParametersMatcher("double", MethodMatchers.ANY)
      .addParametersMatcher("float", MethodMatchers.ANY)
      .build();

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return Collections.singletonList(Tree.Kind.NEW_CLASS);
  }

  @Override
  public void visitNode(Tree tree) {
    if (BIG_DECIMAL_DOUBLE_FLOAT.matches((NewClassTree) tree)) {
      reportIssue(tree, "Use \"BigDecimal.valueOf\" instead.");
    }
  }
}

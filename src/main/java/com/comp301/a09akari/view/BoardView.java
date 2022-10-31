package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BoardView implements FXComponent {
  private AlternateMvcController controller;

  public BoardView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane overall_grid = new GridPane();
    overall_grid.setGridLinesVisible(true);
    overall_grid.setHgap(2);
    overall_grid.setVgap(2);

    for (int i = 0; i < controller.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < controller.getActivePuzzle().getWidth(); j++) {

        Button button = new Button();
        Rectangle square = new Rectangle(20, 20);
        StackPane stack = new StackPane();
        StackPane stack_clue = new StackPane();
        HBox hbox = new HBox();
        int lambdaI = i;
        int lambdaJ = j;

        if (controller.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          square.setFill(Color.WHITE);
          if (controller.isLit(i, j)) {
            square.setFill(Color.LIGHTYELLOW);
          }
          if (controller.isLamp(i, j)) {
            if (controller.isIllegalLamp(i, j)) {
              square.setFill(Color.INDIANRED);
              Text lightbulb = new Text(" " + "\uD83D\uDCA1");
              hbox.getChildren().add(lightbulb);
              hbox.setAlignment(Pos.CENTER);
            } else {
              Text lightbulb = new Text(" " + "\uD83D\uDCA1");
              hbox.getChildren().add(lightbulb);
              hbox.setAlignment(Pos.CENTER);
            }
          }
        } else if (controller.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
          square.setFill(Color.BLACK);
          int clue_value = controller.getActivePuzzle().getClue(i, j);
          Text clue_text = new Text("  " + String.valueOf(clue_value) + " ");
          if (controller.isClueSatisfied(i, j)) {
            clue_text.setFill(Color.LIGHTGREEN);
          } else {
            clue_text.setFill(Color.WHITE);
          }
          hbox.getChildren().add(clue_text);
          stack_clue.getChildren().addAll(square, hbox);
          overall_grid.add(stack_clue, i, j);
        } else {
          Rectangle wall_square = new Rectangle(20, 20);
          wall_square.setFill(Color.BLACK);
          overall_grid.add(wall_square, i, j);
        }
        if (controller.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (controller.isLamp(i, j) || controller.isIllegalLamp(i, j)) {
            stack.getChildren().addAll(square, hbox);
            overall_grid.add(stack, i, j);
          } else {
            overall_grid.add(square, i, j);
          }
        }

        square.setOnMousePressed(
            (MouseEvent event) -> {
              if (controller.isLamp(lambdaI, lambdaJ)) {
                controller.removeBulb(lambdaI, lambdaJ);
              } else {
                controller.clickCell(lambdaI, lambdaJ);
              }
            });

        stack.setOnMousePressed(
            (MouseEvent event) -> {
              if (controller.isLamp(lambdaI, lambdaJ)) {
                controller.removeBulb(lambdaI, lambdaJ);
              } else {
                controller.clickCell(lambdaI, lambdaJ);
              }
            });
      }
    }
    return overall_grid;
  }
}

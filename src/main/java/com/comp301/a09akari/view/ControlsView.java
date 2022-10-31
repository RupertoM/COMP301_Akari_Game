package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ControlsView implements FXComponent {
  private AlternateMvcController controller;
  private HBox controls;

  public ControlsView(AlternateMvcController Controller) {
    this.controller = Controller;
  }

  @Override
  public Parent render() {
    controls = new HBox();
    Button previous = new Button("\u2190");
    previous.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    Button next = new Button("\u2192");
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    Button reset = new Button();
    reset.setText("reset");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    Text puzzle_index =
        new Text(
            "\tPuzzle "
                + (controller.getActivePuzzleIndex() + 1)
                + "/"
                + controller.getPuzzleLibrarySize()
                + "\t");
    Button randomize = new Button();
    randomize.setText("random");
    randomize.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });

    controls.getChildren().addAll(previous, next, puzzle_index, reset, randomize);
    return controls;
  }
}

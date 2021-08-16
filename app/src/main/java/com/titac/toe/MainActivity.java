package com.titac.toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isEnd = false;
    private CheckRowColumn arrangemetOfCells;
    private int turn;
    private TextView titleTv;
    private int total;
    private GridLayout gLayout;
    private Handler handler = new Handler();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUpUI();
    }


    public static class CheckRowColumn {

        private int[][] matrix;

        public CheckRowColumn() {
            matrix = new int[3][3];
        }

        public void set(int rowIndex, int colIndex, int data) {
            matrix[rowIndex][colIndex] = data;
        }

        public int get(int rowIndex, int colIndex) {
            return matrix[rowIndex][colIndex];
        }
    }

    private void setUpUI() {
        arrangemetOfCells = new CheckRowColumn();
        titleTv = findViewById(R.id.titleTv);
        gLayout = findViewById(R.id.grid);
        total = 0;
        turn = 1;
        titleTv.setText("Player 1 turn");
    }

    private boolean checkForGameEnd(int playerCode) {

        boolean win = false;
        //not a good method as it only support 3x3 cells - use nested for loop
        boolean row1 = (arrangemetOfCells.get(0, 0) == playerCode && arrangemetOfCells.get(1, 0) == playerCode && arrangemetOfCells.get(2, 0) == playerCode);
        boolean row2 = (arrangemetOfCells.get(0, 1) == playerCode && arrangemetOfCells.get(1, 1) == playerCode && arrangemetOfCells.get(2, 1) == playerCode);
        boolean row3 = (arrangemetOfCells.get(0, 2) == playerCode && arrangemetOfCells.get(1, 2) == playerCode && arrangemetOfCells.get(2, 2) == playerCode);

        boolean col1 = (arrangemetOfCells.get(0, 0) == playerCode && arrangemetOfCells.get(0, 1) == playerCode && arrangemetOfCells.get(0, 2) == playerCode);
        boolean col2 = (arrangemetOfCells.get(1, 0) == playerCode && arrangemetOfCells.get(1, 1) == playerCode && arrangemetOfCells.get(1, 2) == playerCode);
        boolean col3 = (arrangemetOfCells.get(2, 0) == playerCode && arrangemetOfCells.get(2, 1) == playerCode && arrangemetOfCells.get(2, 2) == playerCode);

        boolean diag1 = (arrangemetOfCells.get(0, 0) == playerCode && arrangemetOfCells.get(1, 1) == playerCode && arrangemetOfCells.get(2, 2) == playerCode);
        boolean diag2 = (arrangemetOfCells.get(0, 2) == playerCode && arrangemetOfCells.get(1, 1) == playerCode && arrangemetOfCells.get(2, 0) == playerCode);

        //not a good way as it only supports 3x3 cells
        if (row1 || row2 || row3 || col1 || col2 || col3 || diag1 || diag2) {
            win = true;
        }
        return win;
    }
    private String whiIsPlaying = "person";

    public void onClickEvent(View view) {
        if (((Button) view).getText().equals("") && !isEnd) {
            performMatrix(view);

        }
    }
    private int checkHasWon() {

        int winner = 0;
        if (checkForGameEnd(1)) {
            winner = 1;
        } else if (checkForGameEnd(2)) {
            winner = 2;
        }

        //check for player 2
        return winner;
    }

    private void performMatrix(View view) {
        ((Button) view).setBackgroundColor(getResources().getColor(turn == 1 ? R.color.my_red : R.color.my_blue));
        ((Button) view).setText("O");
        if (turn == 1){
            ((Button) view).setText("X");
        }



        total++;

        int id = Integer.parseInt((String) view.getTag());
        int rowIndex = (id - 1) / 3;
        int colIndex = (id - 1) % 3;
        arrangemetOfCells.set(rowIndex, colIndex, turn);

        int winner = checkHasWon();

        if (winner == 1) {
            titleTv.setText("Player 1 wins");
            isEnd = true;
        } else if (winner == 2) {
            titleTv.setText((whiIsPlaying.equals("computer") ? "Computer Wins" : "Player 2 wins"));
            isEnd = true;
        }

        if (total == 9 && winner == 0) {
            titleTv.setText("Draw!");
            isEnd = true;
        }

        if (!isEnd) {
            if (turn == 1) {
                turn = 2;
                titleTv.setText((whiIsPlaying.equals("computer") ? "Computer turns" : "Player 2 turns"));
                if (turn == 2 && whiIsPlaying.equals("computer")) {
                    // simulate the play by delaying
                    handler.postDelayed(() -> computersTurn(), 1000);
                }
            } else {
                turn = 1;
                titleTv.setText("Player 1 turns");
            }
        }
    }


    private void computersTurn() {
        ArrayList<Button> buttons = new ArrayList<>();
        int size = gLayout.getChildCount();
        for (int h = 0; h < size; h++) {
            Button btn = (Button) gLayout.getChildAt(h);
            if (btn.getText() == "") {
                buttons.add(btn);
            }
        }
        if (buttons.size() > 0) {
            Random r = new Random();
            int rand = r.nextInt(buttons.size());
            Button btn = buttons.get(rand);
            performMatrix(btn);
        }

    }


}
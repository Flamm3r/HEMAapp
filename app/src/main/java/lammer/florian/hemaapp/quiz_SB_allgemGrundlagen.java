package lammer.florian.hemaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class quiz_SB_allgemGrundlagen extends AppCompatActivity {

    private int progress = 0;   //Tracks the actual Progress in % for the Progressbar
    private int frageNr = 0;    //Tracks the actual Questionnumber
    private int punkte = 0;     //Tracks the number of right questions
    private int[] array_rightAnsw = {1,1,3,2,3,2,1,3}; //Array für die richtigen Antworten --> Wert gibt die richtige Antwort nach Reihenfolge im XML Array pro Frage an
    private String[] titleArray;     //Array für die Titel der Fragen
    final private int[] imageArray = {0, R.drawable.guard_1st, R.drawable.guard_2nd, R.drawable.guard_3nd, R.drawable.guard_4th, R.drawable.guard_5th, R.drawable.guard_6th, R.drawable.guard_7th}; //, R.drawable., R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};   //Array für die Bilder zu den Fragen
    private String[] fragen;    //Initialisierung eines Arrays für die Fragen aus dem XML
    private String[] antworten; //Initialisierung eines Array für die Antworten aus dem XML
    private boolean init = false;


    //Objekt mit den Methoden für das Quiz
    final QuizMethods qM = new QuizMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_sb_allgemgrundlagen);

        final TextView title = (TextView) findViewById(R.id.q_title);
        final ImageView image = (ImageView) findViewById(R.id.q_imageView);
        final TextView question = (TextView) findViewById(R.id.q_textView);
        final Button b_a1 = (Button) findViewById(R.id.b_a1);
        final Button b_a2 = (Button) findViewById(R.id.b_a2);
        final Button b_a3 = (Button) findViewById(R.id.b_a3);
        final ProgressBar pBar = (ProgressBar) findViewById(R.id.pBar);


        //Titel der Fragen
        titleArray = getResources().getStringArray(R.array.allgemGrundTitel);
        //Fragen-Array mit Resourcen aus dem XML füllen
        fragen = getResources().getStringArray(R.array.allgemGrundQuestions);
        //Antworten-Array mit den Ressourcen aus dem XML füllen --> zu beachten ist, dass immer 3 Antworten zu einer Frage gehören: Frage1 --> Antwort Array1-3, Frage 2 --> Antwort Array 4-6, ...
        antworten = getResources().getStringArray(R.array.allgemGrundAnswers);

        //LayoutListener der überprüft ob das Layout fertig erstellt ist, ehe er die Frage im TextView und die Antworten auf den Butten, sowie deren Anordnung festlegt
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.LayoutQandA);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d("HEMAapp", "Initialisiert: " + init);
                //Interface mit richtigen String Werten für Frage und Antworten initialisieren
                updateForNewQustion(title, image, question, pBar, b_a1, b_a2, b_a3);
                //Antwortbuttons werden zufällig angeordnet

                //Test
                qM.switchButtons(b_a1, b_a2, b_a3);

                //switchButtons(b_a1, b_a2, b_a3);

                Log.d("HEMAapp", "Länge des Antworten Arrays: " + array_rightAnsw.length);
                Log.d("HEMAapp", "Initialisiert: " + init);
            }
        });



        //Button Antwort 1
        b_a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HEMAapp", "Button 1 klicked");             //DEBUG
                //checkAnswer(1);                                    //Betrifft nur den gewählten Button um Antwort zu überpüfen
                punkte = qM.checkAnswer(1, frageNr, array_rightAnsw, punkte, getApplicationContext());
                updateForNewQustion(title, image, question, pBar, b_a1, b_a2, b_a3);      //Aktualisiert die Antworten aller Buttons und die Frage

                //Test;
                qM.switchButtons(b_a1, b_a2, b_a3);

                //switchButtons(b_a1, b_a2, b_a3);                            //Vertauscht die Buttonpositionen nach zufälliger Ordnung

            }
        });

        //Button Antwort 2
        b_a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HEMAapp", "Button 2 klicked");
                //checkAnswer(2);
                punkte = qM.checkAnswer(2, frageNr, array_rightAnsw, punkte, getApplicationContext());
                updateForNewQustion(title, image, question, pBar, b_a1, b_a2, b_a3);

                //Test
                qM.switchButtons(b_a1, b_a2, b_a3);

                //switchButtons(b_a1, b_a2, b_a3);

            }
        });

        //Button Antwort 3
        b_a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HEMAapp", "Button 3 klicked");
                //checkAnswer(3);
                punkte = qM.checkAnswer(3, frageNr, array_rightAnsw, punkte, getApplicationContext());
                updateForNewQustion(title, image, question, pBar, b_a1, b_a2, b_a3);

                //Test
                qM.switchButtons(b_a1, b_a2, b_a3);

                //switchButtons(b_a1, b_a2, b_a3);

            }
        });




    }



    //################
    //### Methoden ###
    //################

    //Methode überprüft ob der Button der richtigen Antwort entspricht und tracked den Fortschritt
    /*
    private void checkAnswer(int ButtonNr) {
        //TODO: Index für 5. Frage iwie anpassen, dass die app nicht abstürzt

        if (frageNr < array_rightAnsw.length){     //Sonst wird bei der letzten Frage eine ArrayIndexOutOfBoundsException geschmissen
            //Toastmessage, je nachdem ob richtig oder falsch
            if (array_rightAnsw[frageNr] == ButtonNr){
                Toast.makeText(getApplicationContext(), R.string.ToastRightAnswer, Toast.LENGTH_SHORT).show();
                punkte = punkte + 1;
            }else {
                qM.wrongAnswerDialog(frageNr, this);
                //wrongAnswerDialog(frageNr);
                Toast.makeText(getApplicationContext(), R.string.ToastWrongAnswer, Toast.LENGTH_SHORT).show();
            }
        }

    }
    */

    private void updateForNewQustion(TextView titleVar, ImageView imageVar, TextView questionVar, ProgressBar pBarVar, Button b_answ1, Button b_answ2, Button b_answ3){

        if(frageNr != array_rightAnsw.length){
            //Aktualisierung der Fragen und Antworten
            titleVar.setText(titleArray[frageNr]);
            imageVar.setImageResource(imageArray[frageNr]);
            questionVar.setText(fragen[frageNr]);               //Aktualisiert die Frage in der TextView

            int arrayIndexAnswer_b1 = 0 + (frageNr)*3;        //Berrechnet auf Basis der Button-Nummer-1 (Array beginnt bei 0!) und der Frage-Nummer (Beginnt auch bei 0) den richtigen Indexwert für die Antwort im XML-Array
            int arrayIndexAnswer_b2 = 1 + (frageNr)*3;
            int arrayIndexAnswer_b3 = 2 + (frageNr)*3;

            b_answ1.setText(antworten[arrayIndexAnswer_b1]);    //Aktualisert die Antwort auf dem Button
            b_answ2.setText(antworten[arrayIndexAnswer_b2]);
            b_answ3.setText(antworten[arrayIndexAnswer_b3]);


            frageNr = frageNr + 1;

            //Aktualisierung der Fortschrittsvariablen
            if(init == true) {
                progress = progress + (100/array_rightAnsw.length);
                pBarVar.setProgress(progress);
                Log.d("HEMAapp", "FrageNr und Progress erhöht! FrageNr = " + frageNr);
            }else{
                init = true;
                Log.d("HEMAapp", "init auf true gesetzt. FrageNr = " + frageNr);
            }
        }else {
            progress = progress + (100/array_rightAnsw.length);
            pBarVar.setProgress(progress);
            allQuestionsAnsweredDialog();
        }

    }

    /*

    private void switchButtons(Button b_answ1, Button b_answ2, Button b_answ3){
        //Aktuelle Postion von Button 1
        float b_a1_posX = b_answ1.getX();
        float b_a1_posY = b_answ1.getY();
        //Aktuelle Position von Button 2
        float b_a2_posX = b_answ2.getX();
        float b_a2_posY = b_answ2.getY();
        //Aktuelle Position von Button 3
        float b_a3_posX = b_answ3.getX();
        float b_a3_posY = b_answ3.getY();

        //DEBUG
        Log.d("HEMAapp", "A1: PosX " + b_a1_posX);
        Log.d("HEMAapp", "A1: PosY " + b_a1_posY);
        Log.d("HEMAapp", "A2: PosX " + b_a2_posX);
        Log.d("HEMAapp", "A2: PosY " + b_a2_posY);
        Log.d("HEMAapp", "A3: PosX " + b_a3_posX);
        Log.d("HEMAapp", "A3: PosY " + b_a3_posY);

        //Random Number
        Random rand = new Random();
        int rand_a = 0;
        int rand_b = 0;

        //Wird solange ausgeführt bis zwei unterschiedliche, zufällige Zahlen zwischen 1-3 gewählt sind
        while (rand_a == rand_b){
            rand_a = rand.nextInt(3)+1;
            rand_b = rand.nextInt(3)+1;
        }

        //DEBUG
        Log.d("HEMAapp", "rand_a: " + rand_a);
        Log.d("HEMAapp", "rand_b: " + rand_b);

        //Problem wenn der Button größer als eine Zeile ist, also zu lange Antwort --> wrapcontent verändert die Koordianaten
        if (rand_a == 1 && rand_b == 2){
            b_answ1.setX(b_a2_posX);
            b_answ1.setY(b_a2_posY);
            b_answ2.setX(b_a1_posX);
            b_answ2.setY(b_a1_posY);
        }else if (rand_a == 2 && rand_b == 3){
            b_answ2.setX(b_a3_posX);
            b_answ2.setY(b_a3_posY);
            b_answ3.setX(b_a2_posX);
            b_answ3.setY(b_a2_posY);
        }else if (rand_a == 3 && rand_b == 1){
            b_answ3.setX(b_a1_posX);
            b_answ3.setY(b_a1_posY);
            b_answ1.setX(b_a3_posX);
            b_answ1.setY(b_a3_posY);
        }else if (rand_a == 1 && rand_b == 3){
            b_answ1.setX(b_a3_posX);
            b_answ1.setY(b_a3_posY);
            b_answ3.setX(b_a1_posX);
            b_answ3.setY(b_a1_posY);
        }else{ //(rand_a == 2 && rand_b == 3)
            b_answ2.setX(b_a1_posX);
            b_answ2.setY(b_a1_posY);
            b_answ1.setX(b_a2_posX);
            b_answ1.setY(b_a2_posY);
        }

    }

    */

    /*
    //Erstellt einen Alert Dialog, wenn die falsche Antwort ausgewählt wird --> Aufruf in der checkAnswer() Methode
    private void wrongAnswerDialog(int questionNr){
        String[] rightAnswers = getResources().getStringArray(R.array.AllgemGrund_rightAnswers);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.AlertDialogWrongAnswerTitle)
                .setMessage(rightAnswers[questionNr])
                .setPositiveButton(R.string.AlertDialogButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
    */

    //TODO String Resourcen extrahieren
    //Erstellt einen AlertDialog der zu Quizoverview führt, wenn alle Fragen beantwortet wurden
    private void allQuestionsAnsweredDialog(){
        if (frageNr == array_rightAnsw.length) {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.AlertDialogAllgemGrundAbgeschlossen)
                    .setMessage("Gratuliere, du hast die Allgemeinen Grundlagen mit " + punkte + " von " + array_rightAnsw.length + " Punkten abgeschlossen!")
                    .setPositiveButton(R.string.AlertDialogButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), QuizOverview.class);
                            finish();
                            startActivity(intent);
                        }
                    }).show();
        }
    }



}

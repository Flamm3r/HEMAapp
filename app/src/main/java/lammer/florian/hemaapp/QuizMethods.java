package lammer.florian.hemaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizMethods {

    /*

    //################
    //### Methoden ###
    //################

    //Methode überprüft ob der Button der richtigen Antwort entspricht und tracked den Fortschritt
    public void checkAnswer(int ButtonNr) {
        //TODO: Index für 5. Frage iwie anpassen, dass die app nicht abstürzt

        if (frageNr < array_rightAnsw.length){     //Sonst wird bei der letzten Frage eine ArrayIndexOutOfBoundsException geschmissen
            //Toastmessage, je nachdem ob richtig oder falsch
            if (array_rightAnsw[frageNr] == ButtonNr){
                Toast.makeText(getApplicationContext(), R.string.ToastRightAnswer, Toast.LENGTH_SHORT).show();
                punkte = punkte + 1;
            }else {
                wrongAnswerDialog(frageNr);
                Toast.makeText(getApplicationContext(), R.string.ToastWrongAnswer, Toast.LENGTH_SHORT).show();
            }
        }




    }

    public void updateForNewQustion(TextView titleVar, ImageView imageVar, TextView questionVar, ProgressBar pBarVar, Button b_answ1, Button b_answ2, Button b_answ3){

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
    */


    public void switchButtons(Button b_answ1, Button b_answ2, Button b_answ3){
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



    //Erstellt einen Alert Dialog, wenn die falsche Antwort ausgewählt wird --> Aufruf in der checkAnswer() Methode
    public void wrongAnswerDialog(int questionNr, Context context){
        String[] rightAnswers = context.getResources().getStringArray(R.array.AllgemGrund_rightAnswers);

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.AlertDialogWrongAnswerTitle)
                .setMessage(rightAnswers[questionNr])
                .setPositiveButton(R.string.AlertDialogButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /*

    //TODO String Resourcen extrahieren
    //Erstellt einen AlertDialog der zu Quizoverview führt, wenn alle Fragen beantwortet wurden
    public void allQuestionsAnsweredDialog(){
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
    */

}

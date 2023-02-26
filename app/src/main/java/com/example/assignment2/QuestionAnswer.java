package com.example.assignment2;

public class QuestionAnswer {

    private String area, question, answer, answerChoices;


    public QuestionAnswer(){
    }

    public QuestionAnswer(String area, String question, String answer, String answerChoices){
        this.area = area;
        this.question = question;
        this.answer = answer;
        this.answerChoices = answerChoices;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(String answerChoices) {
        this.answerChoices = answerChoices;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "area='" + area + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", answerChoices='" + answerChoices + '\'' +
                '}';
    }
}

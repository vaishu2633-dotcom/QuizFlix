package com.example.quizflix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank {

    public static class Question {
        public final String text;
        public final String[] options;
        public final int answer;

        public Question(String text, String[] options, int answer) {
            this.text = text;
            this.options = options;
            this.answer = answer;
        }
    }

    private static void add(
            List<Question> list,
            String question,
            int correctAnswer,
            String... options) {

        list.add(new Question(question, options, correctAnswer));
    }

    public static List<Question> getQuestions(String category) {

        List<Question> questions = new ArrayList<>();

        if (category.equals("GK") || category.equals("Mixed")) {
            add(questions, "What is the capital of India?", 1,
                    "Mumbai", "New Delhi", "Pune", "Kolkata");
            add(questions, "Which planet is called the Red Planet?", 2,
                    "Earth", "Venus", "Mars", "Jupiter");
            add(questions, "Who wrote India's National Anthem?", 0,
                    "Rabindranath Tagore", "Mahatma Gandhi",
                    "Jawaharlal Nehru", "Subhas Chandra Bose");
            add(questions, "Which is the largest ocean?", 3,
                    "Atlantic", "Indian", "Arctic", "Pacific");
            add(questions, "How many continents are there?", 2,
                    "Five", "Six", "Seven", "Eight");
            add(questions, "Which is the smallest prime number?", 1,
                    "1", "2", "3", "4");
            add(questions, "Which gas do plants absorb for photosynthesis?", 0,
                    "Carbon dioxide", "Oxygen", "Nitrogen", "Hydrogen");
            add(questions, "Which country is famous for the Eiffel Tower?", 2,
                    "Italy", "Germany", "France", "Spain");
            add(questions, "What is the chemical symbol for gold?", 1,
                    "Ag", "Au", "Fe", "Cu");
            add(questions, "Which organ pumps blood in the body?", 3,
                    "Liver", "Brain", "Lungs", "Heart");
            add(questions, "What is the largest planet in our solar system?", 0,
                    "Jupiter", "Saturn", "Earth", "Neptune");
            add(questions, "Which instrument measures temperature?", 2,
                    "Barometer", "Hygrometer", "Thermometer", "Compass");
            add(questions, "How many sides does a hexagon have?", 1,
                    "Five", "Six", "Seven", "Eight");
            add(questions, "Which is the national animal of India?", 0,
                    "Bengal tiger", "Lion", "Elephant", "Leopard");
            add(questions, "Which is the closest star to Earth?", 3,
                    "Sirius", "Polaris", "Proxima Centauri", "Sun");
        }

        if (category.equals("CS") || category.equals("Mixed")) {
            add(questions, "Which data structure follows FIFO?", 1,
                    "Stack", "Queue", "Tree", "Graph");
            add(questions, "What does CPU stand for?", 0,
                    "Central Processing Unit", "Computer Personal Unit",
                    "Central Program Utility", "Control Processing Unit");
            add(questions, "Which data structure follows LIFO?", 2,
                    "Queue", "Array", "Stack", "Graph");
            add(questions, "What does DBMS stand for?", 0,
                    "Database Management System", "Data Backup System",
                    "Digital Base Management", "Database Machine Software");
            add(questions, "Which language is commonly used in Android apps?", 3,
                    "HTML", "CSS", "SQL", "Java");
            add(questions, "Which sorting algorithm uses divide and conquer?", 1,
                    "Bubble Sort", "Merge Sort", "Selection Sort", "Insertion Sort");
            add(questions, "Which keyword creates an object in Java?", 2,
                    "class", "void", "new", "return");
            add(questions, "What does SQL stand for?", 0,
                    "Structured Query Language", "Simple Query Logic",
                    "System Queue Language", "Standard Quick Language");
            add(questions, "Which protocol is used to transfer web pages?", 3,
                    "FTP", "SMTP", "SSH", "HTTP");
            add(questions, "Which memory is volatile?", 1,
                    "ROM", "RAM", "SSD", "Hard Disk");
            add(questions, "What is the binary representation of decimal 5?", 2,
                    "100", "110", "101", "111");
            add(questions, "Which OOP concept hides internal details?", 0,
                    "Encapsulation", "Inheritance", "Polymorphism", "Recursion");
            add(questions, "Which symbol is used for pointers in C++ declarations?", 3,
                    "&", "#", "@", "*");
            add(questions, "What is the time complexity of binary search?", 1,
                    "O(n)", "O(log n)", "O(n²)", "O(1)");
            add(questions, "Which structure represents hierarchical data?", 2,
                    "Queue", "Stack", "Tree", "Array");
        }

        if (category.equals("AI") || category.equals("Mixed")) {
            add(questions, "What does AI stand for?", 0,
                    "Artificial Intelligence", "Automatic Internet",
                    "Advanced Integration", "Artificial Internet");
            add(questions, "Which algorithm is used for classification?", 2,
                    "K-Means", "PCA", "Logistic Regression", "Apriori");
            add(questions, "What is supervised learning?", 0,
                    "Learning with labeled data", "Learning without labels",
                    "Random learning", "Learning without training");
            add(questions, "Which Python library supports machine learning?", 1,
                    "Flask", "Scikit-learn", "Pygame", "Tkinter");
            add(questions, "What does NLP stand for?", 0,
                    "Natural Language Processing", "Neural Logic Program",
                    "Network Language Protocol", "Natural Learning Program");
            add(questions, "Which algorithm groups similar data points?", 3,
                    "Linear Regression", "Naive Bayes", "Decision Tree", "K-Means");
            add(questions, "What is overfitting?", 1,
                    "Learning too little", "Memorizing training patterns",
                    "Increasing data size", "Reducing model complexity");
            add(questions, "Which metric measures classification accuracy?", 2,
                    "MAE", "RMSE", "Correct predictions / Total predictions", "MSE");
            add(questions, "What is a neural network inspired by?", 0,
                    "Biological nervous systems", "Database tables",
                    "Computer keyboards", "Network cables");
            add(questions, "Which task predicts a continuous value?", 3,
                    "Clustering", "Classification", "Association", "Regression");
            add(questions, "What is the purpose of a training dataset?", 1,
                    "Display predictions", "Learn model parameters",
                    "Store passwords", "Design an interface");
            add(questions, "Which technique reduces feature dimensions?", 2,
                    "KNN", "Naive Bayes", "PCA", "Random Forest");
            add(questions, "What is a confusion matrix used for?", 0,
                    "Evaluating classification", "Storing images",
                    "Compressing files", "Creating databases");
            add(questions, "Which learning method uses rewards?", 3,
                    "Supervised learning", "Clustering",
                    "Dimensionality reduction", "Reinforcement learning");
            add(questions, "What does CNN stand for in deep learning?", 1,
                    "Central Neural Network", "Convolutional Neural Network",
                    "Connected Numeric Node", "Computer Network Node");
        }

        Collections.shuffle(questions);
        return new ArrayList<>(
                questions.subList(0, Math.min(5, questions.size()))
        );
    }
}

package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import project.Author;
import project.Database;
import project.Node;

import javafx.scene.layout.BorderPane;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import project.*;
import sample.CellType;
import sample.Graph;
import sample.Layout;
import sample.Model;
import sample.RandomLayout;

import java.io.IOException;

public class Controller {

    public Button button1;
    private Database db;
    private Graph graph;

    private Connection connection;
    private Main main;

    private CheckBox [] mCheckBoxArray;

    public void buttonclick1(ActionEvent actionEvent) {

        ////데이터 읽는 시점 변경 시도해볼만함
//        db = new Database();
//        db.readFile();

        graph = new Graph();
        System.out.println(db.getAuthorMapByCont(10).toString());


        Stage stage = new Stage();

        BorderPane root = null;
        try {

        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.setCenter(graph.getScrollPane());

            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("graph");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        stage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();


        for(int i=0;i<mCheckBoxArray.length;i++)
        {
            if(mCheckBoxArray[i].isSelected()) {
                System.out.print(mCheckBoxArray[i].getText()+" ");
                System.out.println(mCheckBoxArray[i].isSelected());
            }
        }

    }
    private void addGraphComponents() {

        //SimpleWeightedGraph<Node, DefaultWeightedEdge> weightedGraph = db.getCoauthorWeightedGraph(new Author("Massimo De Gregorio"));
        SimpleWeightedGraph<Node, DefaultWeightedEdge> weightedGraph = db.getCoauthorWeightedGraph(new Author("AAA"));

        Model model = graph.getModel();

        graph.beginUpdate();


        for(Node author:weightedGraph.vertexSet())
        {
            model.addCell(author.getName(), CellType.LABEL);
        }

        for(DefaultWeightedEdge edge :weightedGraph.edgeSet()) {
            if(weightedGraph.getEdgeWeight(edge)>1)
                model.addEdge(weightedGraph.getEdgeTarget(edge).getName(), weightedGraph.getEdgeSource(edge).getName(), weightedGraph.getEdgeWeight(edge));
        }

        graph.endUpdate();
    }

    public void Authorityscreen(ActionEvent actionEvent) {
    }

    public void initManager(Main main) {
        db = new Database();
        db.readFile();
        this.main = main;
        mCheckBoxArray = main.checkBoxArray;


    }

    public void sendConnection(Connection connection) {

        this.connection = connection;

    }
}

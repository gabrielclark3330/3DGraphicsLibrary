# 3DGraphicsLibrary

## Getting started
To start this program no external libraries are needed, just run the Displayer.java file and interact with the screen that is created. 

## Contained files 

### [Displayer.java](3DGraphicsLibrary/src/main/Displayer.java)
This is the starting point for the graphics engine. Displayer is in charge of setting the size of the screen, name of screen, fireing the timer set in Surface.java, and creating a Surface object to run the screen.

### [Surface.java](3DGraphicsLibrary/src/main/Surface.java)
This file draws the stored points to screen with the desired perspective and rotation/translation. It also sets up the sliders that controll roation and the timer that refreshes each frame of the simulation.

### [MatrixLibrary.java](3DGraphicsLibrary/src/main/MatrixLibrary.java)
A storage library for all of the matrix oporations needed in 3D graphics.

### [Point.java](3DGraphicsLibrary/src/main/Point.java)
A class to hold points in a simple format accessed like p.x, p.x ...

### [Triangle.java](3DGraphicsLibrary/src/main/Triangle.java)
Class that holds 3 points and a color assosiated with a triangle.

### [TriangleModels.java](3DGraphicsLibrary/src/main/TriangleModels.java)
A storage library that holds different models made of triangles.

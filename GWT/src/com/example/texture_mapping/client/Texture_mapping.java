/**  
 * 
 *  init texture based on:
 * Copyright 2009-2010 Sönke Sothmann, Steffen Schäfer and others
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.texture_mapping.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLTexture;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class Texture_mapping implements EntryPoint {
        
	   private WebGLRenderingContext glContext;
       private WebGLProgram shaderProgram;
       private int vertexPositionAttribute;
       private WebGLBuffer vertexBuffer;
       
       // Texture
       private WebGLBuffer vertexTextureCoordBuffer;
       private WebGLUniformLocation textureUniform;
       private int textureCoordAttribute;
       private WebGLTexture texture;       

       public void onModuleLoad() {
               final Canvas webGLCanvas = Canvas.createIfSupported();
               webGLCanvas.setCoordinateSpaceHeight(500); // ToDo: AutoSize
               webGLCanvas.setCoordinateSpaceWidth(500);
               glContext = (WebGLRenderingContext) webGLCanvas.getContext("experimental-webgl");
               if(glContext == null) {
            	   glContext = (WebGLRenderingContext)    webGLCanvas.getContext("experimental-webgl");
               }
               
               if(glContext == null) {
                       Window.alert("Sorry, Your Browser doesn't support WebGL!");
               }
               
               glContext.viewport(0, 0, 500, 500);
               
               RootPanel.get("gwtGL").add(webGLCanvas);
               start();
       }
       
       private void start() {
           initShaders();
           glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
           glContext.clearDepth(1.0f);
           checkErrors();
           glContext.enable(WebGLRenderingContext.DEPTH_TEST);
           checkErrors();
           glContext.depthFunc(WebGLRenderingContext.LEQUAL);
           checkErrors();
           initBuffers();
           checkErrors();
           //not supported everywhere// glContext.enable(WebGLRenderingContext.TEXTURE_2D);
           //checkErrors();
           initTexture();

           drawScene();
   }       
       
       private void initTexture() {
		// TODO Auto-generated method stub
    	   checkErrors();
		WebGLTexture texture = glContext.createTexture();
		checkErrors();
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		checkErrors();
		//GWT.create(Resource.class);
		PlaceholderImages placeholderImages= PlaceholderImages.INSTANCE;
		Image image = new Image(placeholderImages.myImage());
		checkErrors();
        glContext.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0, WebGLRenderingContext.RGBA, WebGLRenderingContext.RGBA, WebGLRenderingContext.UNSIGNED_BYTE
        		///, getImage((PlaceholderImages.INSTANCE).myImage()).getElement());
        		, image.getElement());
        checkErrors(); //ERror
        glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MAG_FILTER, WebGLRenderingContext.LINEAR);
        checkErrors(); //ERror
        glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MIN_FILTER, WebGLRenderingContext.LINEAR);
        checkErrors(); //ERror
        glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
        checkErrors(); //ERror
	}

       /**
        * Handles image loading.
        * @param imageResource
        * @return {@link Image} to be used as a texture
        */
       public Image getImage(final ImageResource imageResource) {
               final Image img = new Image();
               img.addLoadHandler(new LoadHandler() {
                       @Override
                       public void onLoad(LoadEvent event) {
                               RootPanel.get().remove(img);
                       }
               });
               img.setVisible(false);
               RootPanel.get().add(img);

               img.setUrl(imageResource.getURL());
               
               return img;
       }       
       
       /**
        * Checks the WebGL Errors and throws an exception if there is an error.
        */
       private void checkErrors() {
               int error = glContext.getError();
               if (error != WebGLRenderingContext.NO_ERROR) {
                       String message = "WebGL Error: " + error;
                       ///GWT.log(message, null);
                      throw new RuntimeException(message);
               }
       }       
       
	public void initShaders() {
           WebGLShader fragmentShader = getShader(WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE.fragmentShader().getText());
           WebGLShader vertexShader = getShader(WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE.vertexShader().getText());

           shaderProgram = glContext.createProgram();
           glContext.attachShader(shaderProgram, vertexShader);
           glContext.attachShader(shaderProgram, fragmentShader);
           glContext.linkProgram(shaderProgram);

           if (!glContext.getProgramParameterb(shaderProgram, WebGLRenderingContext.LINK_STATUS)) {
                   throw new RuntimeException("Could not initialise shaders");
           }

           glContext.useProgram(shaderProgram);

           vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");
           glContext.enableVertexAttribArray(vertexPositionAttribute);
           
           checkErrors();
           this.textureCoordAttribute = glContext.getAttribLocation(shaderProgram, "aTextureCoord"); //"texPosition"); // ToDo check Identifier
           checkErrors(); // no error
           glContext.enableVertexAttribArray(this.textureCoordAttribute);
           checkErrors();      // Error
   }
       
       private WebGLShader getShader(int type, String source) {
           WebGLShader shader = glContext.createShader(type);

 
           glContext.shaderSource(shader, source);
           glContext.compileShader(shader);

           if (!glContext.getShaderParameterb(shader, WebGLRenderingContext.COMPILE_STATUS)) {
                   throw new RuntimeException(glContext.getShaderInfoLog(shader));
           }
           checkErrors();
           return shader;
   }       
       
       private void initBuffers() {
           vertexBuffer = glContext.createBuffer();
           glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
           float[] vertices = new float[]{
                            0.0f,  1.0f,  -5.0f, // first vertex
                           -1.0f, -1.0f,  -5.0f, // second vertex
                            1.0f, -1.0f,  -5.0f  // third vertex
           };
           glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(vertices), WebGLRenderingContext.STATIC_DRAW);
           checkErrors();
           float[] verticesTexture = new float[]{
                   0.0f,  1.0f,   // first vertex
                  1.0f, 1.0f,   // second vertex
                   1.0f, 0.0f  // third vertex
  };        
           vertexTextureCoordBuffer = glContext.createBuffer();
           glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexTextureCoordBuffer);
           glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(verticesTexture ), WebGLRenderingContext.STATIC_DRAW);
           checkErrors();
   }
       
       private void drawScene() {
           glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);
           float[] perspectiveMatrix = createPerspectiveMatrix(45, 1, 0.1f, 1000);
           WebGLUniformLocation uniformLocation = glContext.getUniformLocation(shaderProgram, "perspectiveMatrix");
           glContext.uniformMatrix4fv(uniformLocation, false, perspectiveMatrix);
           glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
           
           // Bind the texture to texture unit 0
           glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
           glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);

           // Point the uniform sampler to texture unit 0
           glContext.uniform1i(textureUniform, 0);           
           
           glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
   }
      
       private float[] createPerspectiveMatrix(int fieldOfViewVertical, float aspectRatio, float minimumClearance, float maximumClearance) {
           float top    = minimumClearance * (float)Math.tan(fieldOfViewVertical * Math.PI / 360.0);
           float bottom = -top;
           float left   = bottom * aspectRatio;
           float right  = top * aspectRatio;

           float X = 2*minimumClearance/(right-left);
           float Y = 2*minimumClearance/(top-bottom);
           float A = (right+left)/(right-left);
           float B = (top+bottom)/(top-bottom);
           float C = -(maximumClearance+minimumClearance)/(maximumClearance-minimumClearance);
           float D = -2*maximumClearance*minimumClearance/(maximumClearance-minimumClearance);

           return new float[]{     X, 0.0f, A, 0.0f,
                                                   0.0f, Y, B, 0.0f,
                                                   0.0f, 0.0f, C, -1.0f,
                                                   0.0f, 0.0f, D, 0.0f};
   };       
       
       
       
       
}
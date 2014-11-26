package com.example.texture_mapping.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface PlaceholderImages extends ClientBundle {
	  @Source("test.png")
	    ImageResource myImage();

	    public static final PlaceholderImages INSTANCE = GWT.create(PlaceholderImages.class);
	    

	}


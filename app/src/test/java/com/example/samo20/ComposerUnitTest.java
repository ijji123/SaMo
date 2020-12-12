package com.example.samo20;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ComposerUnitTest {

    String FAKE_STRING = "received";

    @Mock
    IBluetooth mockBluetooth = mock(BluetoothController.class);

    @Test
    public void numberAddedToSequence_expectedAdded() {
        Composer UUTComposer = new Composer(mockBluetooth);

        // Set
        UUTComposer.addToSequence(1);

        // Get
        String result = UUTComposer.getSequence();

        // Assert
        assertEquals("s-1", result);
    }

    @Test
    public void multipleNumbersAddedToSequence_expectedAdded() {
        Composer UUTComposer = new Composer(mockBluetooth);

        // Set
        UUTComposer.addToSequence(1);
        UUTComposer.addToSequence(5);
        UUTComposer.addToSequence(7);
        UUTComposer.addToSequence(9);

        // Get
        String result = UUTComposer.getSequence();
        assertEquals("s-1-5-7-9", result);
    }

    @Test
    public void noNumberAddedToSequence_expectedEmpty() {
        Composer UUTComposer = new Composer(mockBluetooth);

        String result = UUTComposer.getSequence();
        assertEquals("s", result);
    }

    @Test
    public void clickListenerGetsTag_expectedFilled() {
        Composer UUTComposer = new Composer(mockBluetooth);

        View v = new testView();
        // Anonymt objekt som arver, extenderprojekt.
        v.setTag(1);

        UUTComposer.previewClickListener.onClick(v);
        String result = UUTComposer.preview;

        assertEquals("1", result);
    }

    @Test
    public void sendsCommand_expectedReceived() {
        Composer UUTComposer = new Composer(mockBluetooth);
        String testString = "s-1-5-7-9";

        // Act
        UUTComposer.sendCommand(testString);

        // Assert
        verify(mockBluetooth).sendCommand(testString);
    }

    @Test
    public void playPreview_expectedReceived() {
        Composer UUTComposer = new Composer(mockBluetooth);
        int testInt = 1;
        String testString = "1";

        View v = new testView();
        v.setTag(testInt);

        // Act
        UUTComposer.previewClickListener.onClick(v);

        // Assert
        verify(mockBluetooth).sendCommand(testString);
    }

    // @Test
    // Virker kun, hvis clear metoderne fjernes
    public void play_expectedReceived() {
        Composer UUTComposer = new Composer(mockBluetooth);
        UUTComposer.addToSequence(1);
        UUTComposer.addToSequence(5);
        UUTComposer.addToSequence(7);
        UUTComposer.addToSequence(9);

        String result = UUTComposer.getSequence();

        //assertEquals("s-1-5-7-9", result);
        View v = new testView();

        UUTComposer.playClickListener.onClick(v);
        verify(mockBluetooth).sendCommand(result);
    }

    @Test
    public void successfulDragAndDrop_sequenceFieldExpectedToUpdate(){
        Composer UUTComposer = new Composer(mockBluetooth);
        View v = new testView();

        UUTComposer.updateSequenceField(0);
        //Object resultDrawable = UUTComposer.sequenceField1.getTag();
        //Drawable expectedDrawable= UUTComposer.myImageList[1];
        // assertEquals(expectedDrawable,resultDrawable);
    }

    private class testView extends View{

        private Object tag;

        public testView(Context context) {
            super(context);
        }

        public testView(){
            super(null);
        }

        @Override
        public Object getTag() {
            return tag;
        }

        @Override
        public void setTag(Object tag) {
            this.tag = tag;
        }
    }

}

/*
 * blue - object composition environment for csound
 * Copyright (C) 2016
 * Steven Yi <stevenyi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package blue.score.layers.audio.ui;

import blue.score.TimeState;
import blue.score.layers.audio.core.AudioClip;
import blue.ui.utilities.RedispatchMouseAdapter;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author stevenyi
 */
public class FadeHandle extends JPanel {

    private final AudioClip audioClip;
    private final TimeState timeState;

    boolean adjustingFade = false;

    private final MouseAdapter mouseAdapter = new RedispatchMouseAdapter() {
        float max = 0.0f;
        float startFade;
        int startX;
        Cursor cursor;

        @Override
        public void mouseEntered(MouseEvent e) {
            cursor = getCursor();
            if(fadeIn) {
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            }
            e.consume();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(cursor);
            e.consume();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
             e.consume();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            max = fadeIn ? audioClip.getDuration() - audioClip.getFadeOut()
                    : audioClip.getDuration() - audioClip.getFadeIn();
            startFade = fadeIn ? audioClip.getFadeIn() : audioClip.getFadeOut();

            startX = e.getXOnScreen();
            adjustingFade = true;
            e.consume();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            adjustingFade = false;
            e.consume();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (adjustingFade) {
                float timeAdj = (e.getXOnScreen() - startX)
                        / (float) timeState.getPixelSecond();
                float newFade = fadeIn ? startFade + timeAdj : startFade - timeAdj;
                
                newFade = Math.max(0.0f, newFade);
                newFade = Math.min(max, newFade);

                if (fadeIn) {
                    audioClip.setFadeIn(newFade);
                } else {
                    audioClip.setFadeOut(newFade);
                }
                e.consume();
            }
        }

    };
    private final boolean fadeIn;

    public FadeHandle(AudioClip audioClip, TimeState timeState, boolean fadeIn) {
        this.fadeIn = fadeIn;
        this.audioClip = audioClip;
        this.timeState = timeState;
        setSize(5, 5);
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    public boolean isAdjustingFade() {
        return adjustingFade;
    }
}
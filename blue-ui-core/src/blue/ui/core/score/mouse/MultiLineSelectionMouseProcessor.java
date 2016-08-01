/*
 * blue - object composition environment for csound
 * Copyright (c) 2001-2008 Steven Yi (stevenyi@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by  the Free Software Foundation; either version 2 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307 USA
 */
package blue.ui.core.score.mouse;

import blue.ui.core.score.layers.soundObject.*;
import blue.components.AlphaMarquee;
import blue.plugin.ScoreMouseListenerPlugin;
import static blue.score.Score.SPACER;
import blue.score.TimeState;
import blue.score.layers.Layer;
import blue.score.layers.LayerGroup;
import blue.score.layers.ScoreObjectLayer;
import blue.ui.core.render.RealtimeRenderManager;
import blue.ui.core.score.ModeManager;
import blue.ui.core.score.MultiLineScoreSelection;
import blue.ui.core.score.ScoreController;
import blue.ui.core.score.ScoreMode;
import blue.ui.core.score.ScorePath;
import blue.ui.core.score.layers.LayerGroupPanel;
import static blue.ui.core.score.mouse.BlueMouseAdapter.scoreTC;
import blue.ui.utilities.UiUtilities;
import blue.utility.ScoreUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.SwingUtilities;

@ScoreMouseListenerPlugin(displayName = "MultiLineSelectionMouseProcessor",
        position = 100)
class MultiLineSelectionMouseProcessor extends BlueMouseAdapter {

    private Rectangle scrollRect = new Rectangle(0, 0, 1, 1);

    Layer startLayer = null;
    Layer lastLayer = null;
    List<Layer> allLayers = null;
    int startX = -1;
    int[] startTopBottom = null;

    TimeState timeState = null;

    MultiLineScoreSelection selection = MultiLineScoreSelection.getInstance();

    @Override
    public void mousePressed(MouseEvent e) {

        if (!isMultiLineMode()) {
            return;
        }

        AlphaMarquee marquee = scoreTC.getMarquee();
        Point p = SwingUtilities.convertPoint(scoreTC.getScorePanel(),
                e.getPoint(), marquee);

        if (marquee.isVisible() && marquee.contains(p)) {
            return;
        }

        e.consume();
        RealtimeRenderManager.getInstance().stopAuditioning();

        timeState = scoreTC.getTimeState();

        SoundObjectView sObjView;

        ScorePath scorePath = ScoreController.getInstance().getScorePath();

        Layer layer = scorePath.getGlobalLayerForY(e.getY());

        if (layer == null || !(layer instanceof ScoreObjectLayer)) {
            marquee.setVisible(false);
            return;
        }

        startLayer = lastLayer = layer;
        allLayers = scorePath.getAllLayers();

        if (UiUtilities.isRightMouseButton(e)) {
//            showPopup(comp, e);
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            startX = Math.max(e.getX(), 0);

            float startTime = startX / (float) timeState.getPixelSecond();
            if (timeState.isSnapEnabled()) {
                startTime = ScoreUtilities.getSnapValueStart(startTime,
                        timeState.getSnapValue());
                startX = (int) (startTime * timeState.getPixelSecond());
            }

            startTopBottom = getTopBottomForLayer(layer,
                    scorePath.getScore());

            scoreTC.getMarquee().setStart(new Point(startX, startTopBottom[0]));
            scoreTC.getMarquee().setVisible(true);

            ScoreController.getInstance().setSelectedScoreObjects(null);

            selection.reset();
            selection.startSelection(startTime, startTime);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (!isMultiLineMode()) {
            return;
        }

        e.consume();

        AlphaMarquee marquee = scoreTC.getMarquee();

        if (SwingUtilities.isLeftMouseButton(e)) {
            int x = Math.max(e.getX(), 0);
            float mouseDragTime = x / (float) timeState.getPixelSecond();

            if (timeState.isSnapEnabled()) {
                mouseDragTime = ScoreUtilities.getSnapValueMove(mouseDragTime,
                        timeState.getSnapValue());
                x = (int) (mouseDragTime * timeState.getPixelSecond());
            }

            ScorePath scorePath = ScoreController.getInstance().getScorePath();

            Layer layer = scorePath.getGlobalLayerForY(e.getY());

            if (layer != null && (layer instanceof ScoreObjectLayer)) {
                lastLayer = layer;
            }

//            if (!(layer instanceof ScoreObjectLayer)) {
//                return;
//            }
            int[] topBottom = getTopBottomForLayer(lastLayer,
                    scorePath.getScore());

            int leftX, rightX, topY, bottomY;
            int startLayerIndex, endLayerIndex;

            if (x < startX) {
                leftX = x;
                rightX = startX;
            } else {
                leftX = startX;
                rightX = x;
            }
            
            if (topBottom[0] < startTopBottom[0]) {
                topY = topBottom[0];
                bottomY = startTopBottom[1];
                startLayerIndex = allLayers.indexOf(lastLayer);
                endLayerIndex = allLayers.indexOf(startLayer);
            } else {
                topY = startTopBottom[0];
                bottomY = topBottom[1];
                startLayerIndex = allLayers.indexOf(startLayer);
                endLayerIndex = allLayers.indexOf(lastLayer);
            }
            
            leftX = Math.max(leftX, 0);
            topY = Math.max(topY, 0);

            marquee.setStart(new Point(leftX, topY));
            marquee.setDragPoint(new Point(rightX, bottomY));

            float start = leftX / (float) timeState.getPixelSecond();
            float end = rightX / (float) timeState.getPixelSecond();

            marquee.startTime = start;
            marquee.endTime = end;

            List<Layer> currentLayers = allLayers.subList(startLayerIndex,
                    endLayerIndex + 1);

            selection.updateSelection(start, end, currentLayers);
        }

        checkScroll(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isMultiLineMode()) {
            return;
        }

        e.consume();

        ScoreController.getInstance().setSelectedScoreObjects(null);

        if (SwingUtilities.isLeftMouseButton(e)) {
            Component[] comps = scoreTC.getLayerPanel().getComponents();

            for (Component c : comps) {
                if (c instanceof LayerGroupPanel) {
                    ((LayerGroupPanel) c).marqueeSelectionPerformed(
                            scoreTC.getMarquee());
                }
            }
        }
        timeState = null;
    }

    private boolean isMultiLineMode() {
        return ModeManager.getInstance().getMode() == ScoreMode.MULTI_LINE;
    }

    private void checkScroll(MouseEvent e) {
        Point temp = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
                scoreTC.getScorePanel());

        scrollRect.setLocation(temp);

        scoreTC.getScorePanel().scrollRectToVisible(scrollRect);
    }

    protected int[] getTopBottomForLayer(Layer targetLayer, List<LayerGroup<? extends Layer>> allLayers) {
        int runningY = 0;

        for (LayerGroup<? extends Layer> layerGroup : allLayers) {
            for (Layer layer : layerGroup) {
                if (layer == targetLayer) {
                    return new int[]{runningY, runningY + layer.getLayerHeight()};
                }
                runningY += layer.getLayerHeight();
            }
            runningY += SPACER;
        }

        return null;
    }

    @Override
    public boolean acceptsMode(ScoreMode mode) {
        return mode == ScoreMode.MULTI_LINE;
    }
}

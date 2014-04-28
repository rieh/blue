/*
 * blue - object composition environment for csound
 * Copyright (C) 2013
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
package blue.ui.core.score.object.actions;

import blue.score.ScoreObject;
import blue.soundObject.SoundObject;
import blue.ui.core.score.ScoreTopComponent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JColorChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Blue",
        id = "blue.ui.core.score.actions.SetColorAction")
@ActionRegistration(
        displayName = "#CTL_SetColorAction")
@Messages("CTL_SetColorAction=Set Color")
@ActionReference(path = "blue/score/actions", position = 400, separatorAfter = 405)
public final class SetColorAction extends AbstractAction
        implements ContextAwareAction {

    private final Collection<? extends ScoreObject> scoreObjects;
    private final Collection<? extends SoundObject> soundObjects;

    public SetColorAction() {
        this(Utilities.actionsGlobalContext());
    }

    public SetColorAction(Lookup lookup) {
        super(NbBundle.getMessage(SetColorAction.class, "CTL_SetColorAction"));
        scoreObjects = lookup.lookupAll(ScoreObject.class);
        soundObjects = lookup.lookupAll(SoundObject.class);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (soundObjects.size() > 0 && scoreObjects.size() == soundObjects.size()) {

            Color retVal = JColorChooser.showDialog(
                    WindowManager.getDefault().getMainWindow(), "Choose Color",
                    soundObjects.iterator().next().getBackgroundColor());

            if (retVal != null) {
                for (SoundObject sObj : soundObjects) {
                    sObj.setBackgroundColor(retVal);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return scoreObjects.size() == soundObjects.size() &&
                scoreObjects.size() > 0;
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new SetColorAction(actionContext);
    }

}

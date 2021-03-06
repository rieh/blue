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
package blue.orchestra.editor.blueSynthBuilder.jfx;

import blue.orchestra.blueSynthBuilder.BSBLabel;
import javafx.scene.control.Label;

/**
 *
 * @author stevenyi
 */
public class BSBLabelView extends Label {

    private final BSBLabel label;

    public BSBLabelView(BSBLabel label) {
        setUserData(label);

        this.label = label;
        this.setStyle("-fx-fill: white; "
                + "-fx-font-smooth-type: lcd");

        sceneProperty().addListener((obs, old, newVal) -> {
            if (newVal == null) {
                this.textProperty().unbind();
                this.fontProperty().unbind();
            } else {
                this.textProperty().bind(label.labelProperty());
                this.fontProperty().bind(label.fontProperty());
            }
        });
    }
}

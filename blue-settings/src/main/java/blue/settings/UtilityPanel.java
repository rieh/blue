/*
 * blue - object composition environment for csound Copyright (c) 2000-2009
 * Steven Yi (stevenyi@gmail.com)
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.LIB. If not, write to the Free
 * Software Foundation Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307
 * USA
 */

package blue.settings;

import blue.ui.utilities.FileChooserManager;
import blue.ui.utilities.SimpleDocumentListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final class UtilityPanel extends javax.swing.JPanel {
    public static final String CSOUND_EXECUTABLE = "csoundExecutable";
    public static final String FREEZE_FLAGS = "freezeFlags";

    private final UtilityOptionsPanelController controller;

    private boolean loading = false;

    UtilityPanel(UtilityOptionsPanelController controller) {
        this.controller = controller;
        initComponents();

        DocumentListener changeListener = new SimpleDocumentListener() {

            @Override
            public void documentChanged(DocumentEvent e) {
                if(!loading) {
                    UtilityPanel.this.controller.changed();
                }
            }
        };

        csoundExecText.getDocument().addDocumentListener(changeListener);
        freezeFlagsText.getDocument().addDocumentListener(changeListener);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        csoundExecText = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        freezeFlagsText = new javax.swing.JTextField();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(UtilityPanel.class, "UtilityPanel.jLabel1.text_1")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(UtilityPanel.class, "UtilityPanel.jLabel2.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(UtilityPanel.class, "UtilityPanel.jButton1.text_1")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(csoundExecText, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(freezeFlagsText, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csoundExecText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(freezeFlagsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(153, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        List<File> retVal = FileChooserManager.getDefault().showOpenDialog(
                getClass(), this);

        if (!retVal.isEmpty()) {
            File f = retVal.get(0);

            try {
                String path = f.getCanonicalPath();

                if (f.exists() && f.isFile()) {
                    csoundExecText.setText(path);
                    if(!loading) {
                        controller.changed();
                    }
                }
            } catch (IOException ioe) {
                // ignore
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private String getDefaultFreezeFlags() {

        String osName = System.getProperty("os.name");

        String flag = "W";

        if (osName.contains("Mac")) {
            flag = "A";
        }

        return "-" + flag + "do";

    }

    void load() {
        loading = true;

        final UtilitySettings settings = UtilitySettings.getInstance();

        csoundExecText.setText(settings.csoundExecutable);
        freezeFlagsText.setText(settings.freezeFlags);

        loading = false;
    }

    void store() {
        final UtilitySettings settings = UtilitySettings.getInstance();
        settings.csoundExecutable = csoundExecText.getText();
        settings.freezeFlags = freezeFlagsText.getText();
        settings.save();
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField csoundExecText;
    private javax.swing.JTextField freezeFlagsText;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
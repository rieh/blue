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

import blue.ui.utilities.SimpleDocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

final class ProjectDefaultsPanel extends javax.swing.JPanel {

    private final ProjectDefaultsOptionsPanelController controller;

    private boolean loading = false;

    ProjectDefaultsPanel(ProjectDefaultsOptionsPanelController controller) {
        this.controller = controller;
        initComponents();

        DocumentListener changeListener = new SimpleDocumentListener() {

            @Override
            public void documentChanged(DocumentEvent e) {
                if (!loading) {
                    ProjectDefaultsPanel.this.controller.changed();
                }
            }
        };

        defaultAuthorText.getDocument().addDocumentListener(changeListener);
        
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
        defaultAuthorText = new javax.swing.JTextField();
        mixerEnabledCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        layerHeightDefaultComboBox = new javax.swing.JComboBox<>();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ProjectDefaultsPanel.class, "ProjectDefaultsPanel.jLabel1.text_1")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ProjectDefaultsPanel.class, "ProjectDefaultsPanel.jLabel2.text_1")); // NOI18N

        defaultAuthorText.setText(org.openide.util.NbBundle.getMessage(ProjectDefaultsPanel.class, "ProjectDefaultsPanel.defaultAuthorText.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(mixerEnabledCheckBox, org.openide.util.NbBundle.getMessage(ProjectDefaultsPanel.class, "ProjectDefaultsPanel.mixerEnabledCheckBox.text_1")); // NOI18N
        mixerEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mixerEnabledCheckBoxActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ProjectDefaultsPanel.class, "ProjectDefaultsPanel.jLabel3.text")); // NOI18N

        layerHeightDefaultComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        layerHeightDefaultComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layerHeightDefaultComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mixerEnabledCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(defaultAuthorText)
                    .addComponent(layerHeightDefaultComboBox, 0, 139, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(defaultAuthorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mixerEnabledCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(layerHeightDefaultComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mixerEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mixerEnabledCheckBoxActionPerformed
        controller.changed();
}//GEN-LAST:event_mixerEnabledCheckBoxActionPerformed

    private void layerHeightDefaultComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layerHeightDefaultComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_layerHeightDefaultComboBoxActionPerformed

    void load() {
        loading = true;

        ProjectDefaultsSettings settings = ProjectDefaultsSettings.getInstance();

        defaultAuthorText.setText(settings.defaultAuthor);
        mixerEnabledCheckBox.setSelected(settings.mixerEnabled);
        layerHeightDefaultComboBox.setSelectedIndex(settings.layerHeightDefault);
        loading = false;
    }

    void store() {
        ProjectDefaultsSettings settings = ProjectDefaultsSettings.getInstance();

        settings.defaultAuthor = defaultAuthorText.getText();
        settings.mixerEnabled = mixerEnabledCheckBox.isSelected();
        settings.layerHeightDefault = layerHeightDefaultComboBox.getSelectedIndex();

        settings.save();
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField defaultAuthorText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox<String> layerHeightDefaultComboBox;
    private javax.swing.JCheckBox mixerEnabledCheckBox;
    // End of variables declaration//GEN-END:variables
}

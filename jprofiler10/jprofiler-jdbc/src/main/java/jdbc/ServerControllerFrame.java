package jdbc;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

public class ServerControllerFrame extends JFrame {

    /**  */
    private static final long serialVersionUID = -5965626872588763785L;

    private JToggleButton activityButton;
    private JCheckBox leakCheckBox;
    private JdbcDemo demo;

    public ServerControllerFrame(JdbcDemo demo) {
        this.demo = demo;

        createControls();
        buildLayout();
        configureWindow();
    }

    private void createControls() {
        activityButton = new JToggleButton();
        updateButtonText();
        Dimension buttonSize = activityButton.getPreferredSize();
        buttonSize.height *= 2;
        buttonSize.width += 10;
        activityButton.setPreferredSize(buttonSize);
        
        activityButton.addActionListener(e -> {
          checkEnabled();
          updateActivity();
        });

        leakCheckBox = new JCheckBox("Leak connections");
        checkEnabled();
    }

    private void updateActivity() {
        updateButtonText();
        if (activityButton.isSelected()) {
            demo.startActivity(leakCheckBox.isSelected());
        } else {
            demo.stopActivity();
        }
    }

    private void updateButtonText() {
        activityButton.setText((activityButton.isSelected() ? "Stop" : "Start") + " Database Activity");
    }

    private void checkEnabled() {
        leakCheckBox.setEnabled(!activityButton.isSelected());
    }

    private void buildLayout() {
        JComponent contentPane = (JComponent)getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets.top = gc.insets.left = gc.insets.right = 5;
        gc.gridx = 0;
        gc.gridy = 0;
        contentPane.add(new JLabel("An HSQL server has been started."), gc);
        gc.gridy++;
        contentPane.add(new JLabel("To perform continuous database activity, toggle the button below."), gc);

        gc.gridy++;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(activityButton, gc);
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;

        gc.gridy++;
        contentPane.add(leakCheckBox, gc);
    }

    private void configureWindow() {
        setTitle("JDBC Demo");
        setLocation(100, 100);
        pack();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                demo.shutdown();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                if (Boolean.getBoolean("leakConnection")) {
                    leakCheckBox.setSelected(true);
                }
                if (Boolean.getBoolean("startRecording")) {
                    activityButton.setSelected(true);
                    checkEnabled();
                    updateActivity();
                }
            }

        });
    }

}

/**
 *
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 *
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 * @author Dean Ashton, Chris Olive, John Travers
 *
 */

package view.windows;

import managers.FileManager;
import managers.SettingsManager;
import managers.WindowManager;
import managers.ActionManager;

import java.util.logging.Logger;

import utils.Resources;
import utils.Settings;
import utils.jsyntax.JEditTextArea;
import utils.jsyntax.JEditTextAreaWithMouseWheel;
import utils.jsyntax.tokenmarker.HaskellTokenMarker;
import utils.jsyntax.tokenmarker.LHSHaskellTokenMarker;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.KeyStroke;
import javax.swing.text.StyleConstants;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Main window where code is displayed
 */
public class EditorWindow {
  private static Logger log = Logger.getLogger("heat");
  private static JEditTextArea jtaCodeView;
  private WindowManager wm = WindowManager.getInstance();

  /* If the document has been modified */
  private boolean hasBeenModified = false;

  /* If it has been saved */
  private boolean needsSaving;
  private int fontSize = 25;
  
  /* Popup menu for the display window*/
  private JPopupMenu popMenu = new JPopupMenu("Edit");
  /*Popup menu items*/
  private JMenuItem jMenuItemCut = new JMenuItem("Cut");
  private JMenuItem jMenuItemCopy = new JMenuItem("Copy");
  private JMenuItem jMenuItemPaste = new JMenuItem("Paste");
  
  private boolean enabled = true;

  /**
   * Creates a new EditorWindow object.
   */
  public EditorWindow() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void setEnabled(boolean on) {
	  // do not disable GUI, because that would make it nearly invisible
	  // just make it impossible to edit the program
      if (!FileManager.getInstance().isWriteable()) {
          on = false;
      }
      enabled = on;
      jtaCodeView.setEditable(on);
  }
  
  public boolean isEnabled() {
	  return enabled;
  }


  /**
   * Sets the modified status to true. Enables the save option
   * and allows the file to be closed
   */
  public void setModified() {
    hasBeenModified = true;
    // wm.setCloseEnabled(true);
  }

  /**
   * Returns the selected text, or empty string
   *
   * @return the selected text, or empty string
   */
  public String getSelectedText() {
    return jtaCodeView.getSelectedText();
  }

  /**
   * Moves the focus of the code to the given line number
   *
   * @param lineNum The number to focus on
   */
  public void focusLine(int lineNum) {
    int offset = jtaCodeView.getLineStartOffset(lineNum-1);  // jedit counts lines starting with 0, not 1
    jtaCodeView.setCaretPosition(offset);
  }

  /**
   * Cuts any selected text
   */
  public void cut() {
    log.warning("cut: " + getSelectedText());
    jtaCodeView.cut();
  }
  
  /**
   * Copies any selected text
   */
  public void copy() {
    log.warning("copy: " + getSelectedText());
    jtaCodeView.copy();
  }

  
  /**
   * Pastes clipboard contents 
   */
  public void paste() {
    jtaCodeView.paste();
    log.warning("pasted: " + getSelectedText());
 }

  /**
   * Get the JEditTextArea associated with this windoe
   *
   * @return The JEditTextArea in use
   */
  public JEditTextArea getJTextPane() {
    return jtaCodeView;
  }

  public void grabFocus() {
	  jtaCodeView.requestFocusInWindow();
  }
  
  /**
   * Sets a line number to be highlighted
   *
   * @param linenum The line to focus
   */
  public void setLineMark(int linenum) {
    jtaCodeView.getPainter().setLineMark(linenum);
    jtaCodeView.repaint();
  }

  /**
   * Returns the line number of marked line
   *
   * @return the line number of the marked line
   */
  public int getLineMark() {
    return jtaCodeView.getPainter().getLineMark();
  }
  
  /**
   * Returns the text of highlighted line
   *
   * @return the text of the highlighted line
   */
  public String getLineMarkText() {
    int lineNumber = jtaCodeView.getMarkLine();
    return jtaCodeView.getLineText(lineNumber);
  }
  
  

  /**
   * Clears the line mark
   */
  public void clearLineMark() {
    jtaCodeView.getPainter().clearLineMark();
    jtaCodeView.repaint();
  }

  /**
   * Sets up the GUI
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    SettingsManager sm = SettingsManager.getInstance();
    FileManager fm = FileManager.getInstance();
    jtaCodeView = new JEditTextAreaWithMouseWheel();
    jtaCodeView.getPainter().setBackground(Color.BLACK);
    jtaCodeView.getPainter().setLineHighlightColor(Color.gray);
    jtaCodeView.getPainter().setCaretColor(Color.WHITE);
  
    CreateCursor();
   
    

    String fontSizeStr = sm.getSetting(Settings.CODE_FONT_SIZE);

    if ((fontSizeStr != null) && (fontSizeStr != "")) {
      try {
        int size = Integer.parseInt(fontSizeStr);
        fontSize = size;
        setFontSize(fontSize);
      } catch (NumberFormatException nfe) {
        log.warning("[DisplayWindow] - Failed to parse " +
          Settings.CODE_FONT_SIZE + " setting, check value in settings file");
      }
    }

    
    jtaCodeView.setTokenMarker(new HaskellTokenMarker());

    //jtaCodeView.setPreferredSize(new Dimension(500, 200));
    
    
    //adding action listener for each of the menu items
    
    jMenuItemCut.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          cut();
        }
    });
    jMenuItemCopy.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          copy();
        }
    });
    jMenuItemPaste.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          paste();
        }
    });
   
    
    //adding menu items to the popup menu
    popMenu.add(jMenuItemCut);
    popMenu.add(jMenuItemCopy);
    popMenu.add(jMenuItemPaste);
    
    jMenuItemCut.setFont(new Font("Arial",Font.PLAIN,38));
    jMenuItemCopy.setFont(new Font("Arial",Font.PLAIN,38));
    jMenuItemPaste.setFont(new Font("Arial",Font.PLAIN,38));
    
    //sets the popup menu field of the JEditTextArea to the menu just created
    jtaCodeView.setRightClickPopup(popMenu);
    
    jtaCodeView.setHorizontalScrollBarEnabled(enabled);
    setEnabled(false);
}
 

  /**
   * Changes the font size
   *
   * @param ptSize The new size
   */
  public void setFontSize(int ptSize) {
    Font font = new Font("monospaced", Font.PLAIN, ptSize);

    jtaCodeView.getPainter().setFont(font);
    jtaCodeView.repaint();
  }

  /**
   * Displays the content in the display window
   *
   * @param toDisplay content to display
   */
  public void setEditorContent(String toDisplay) {
    jtaCodeView.setText(toDisplay);
    jtaCodeView.setCaretPosition(0);
  }

  /**
   * Returns all code from display window
   *
   * @return the displayed code
   */
  public String getEditorContent() {
	// A bit of a hack to remove spurious \r (without \n) that jtaCodeView may have wrongly inserted.
	String newline = System.getProperty("line.separator");
	String text = jtaCodeView.getText();
	String withoutr = text.replaceAll("\r\n", "\n").replaceAll("\r", "");
	return withoutr.replaceAll("\n", newline);
  }

  /**
   * Returns the {@link JEditTextArea} component
   *
   * @return the {@link JEditTextArea} component 
   */
  public static JEditTextArea getTextPane() {
    return jtaCodeView;
  }
  
  public void CreateCursor() {
		Toolkit t1 = Toolkit.getDefaultToolkit();
		java.net.URL imageURL = Resources.class.getClassLoader().getResource("icons/crossHair.png");
		Image img = t1.getImage(imageURL);
	  	Point point = new Point(20,30);
	  	Cursor cursor = t1.createCustomCursor(img, point, "Cursor");
	  	jtaCodeView.getPainter().setCursor(cursor);
	}
  
  
  
 
}

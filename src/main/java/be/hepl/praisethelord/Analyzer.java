package be.hepl.praisethelord;

import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiFile;

public class Analyzer extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Language lang = file.getLanguage();
        String text = editor.getDocument().getText();
        DialogWrapper dialog = new AnalyzerDialog(text);
        dialog.show();
    }

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setEnabled(editor != null);
    }
}

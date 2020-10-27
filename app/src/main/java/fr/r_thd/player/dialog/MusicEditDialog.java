package fr.r_thd.player.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.r_thd.player.R;
import fr.r_thd.player.model.Music;
import fr.r_thd.player.storage.MusicDatabaseStorage;

public class MusicEditDialog extends DialogFragment {
    private UpdatableFromDialog updatable;
    private Music edited;
    private View view;

    public MusicEditDialog(UpdatableFromDialog updatable, Music edited) {
        this.updatable = updatable;
        this.edited = edited;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_edit_music, null);
        setMusicToView();
        return new AlertDialog.Builder(getActivity())
                .setTitle("Modifier une musique")
                .setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Music music = getMusicFromView();
                        MusicDatabaseStorage.get(getContext()).update(music.getId(), music);
                        updatable.updateFromDialog();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }

    private void setMusicToView() {
        ((EditText) view.findViewById(R.id.music_title)).setText(edited.getTitle());
    }

    private Music getMusicFromView() {
        edited.setTitle(((EditText) view.findViewById(R.id.music_title)).getText().toString());
        return edited;
    }
}

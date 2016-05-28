package mobile.komputer.unsyiah.ac.id.editorsederhana;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Baca isi berkas
        String isiBerkas = bacaBerkas();

        // Ambil txtIsi dan isikan dengan isi berkas
        EditText txtIsi = (EditText) findViewById(R.id.txtIsi);
        txtIsi.setText(isiBerkas);

        // Pindahkan cursor ke akhir text
        txtIsi.setSelection(isiBerkas.length());
    }

    /**
     * Tangani penekanan tombol simpan.
     */
    public void clickBtnSimpan(View view) {
        // Ambil isi dari txtIsi
        EditText txtIsi = (EditText) findViewById(R.id.txtIsi);
        String isi = txtIsi.getText().toString();

        // Tulis isi ke berkas
        simpanBerkas(isi);
    }

    // Nama berkas untuk menyimpan isi editor
    private static final String NAMA_BERKAS = "isi.txt";

    /**
     * Membaca semua isi suatu berkas text.
     *
     * @return Semua isi berkas.
     */
    private String bacaBerkas() {
        // Karena berkas hanya dibaca per baris maka perlu bantuan StringWriter untuk menggabungkan
        // semua baris yang dibaca
        StringWriter stringWriter = new StringWriter();

        FileInputStream berkasStream = null;
        try {
            // Buka berkas untuk dibaca, pakai buffer biar lebih efisien
            berkasStream = openFileInput(NAMA_BERKAS);
            InputStreamReader berkasStreamReader = new InputStreamReader(berkasStream);
            BufferedReader berkasBuffered = new BufferedReader(berkasStreamReader);

            // Tandai ini masih membaca baris pertam
            boolean barisPertama = true;

            String satuBaris = null;
            try {
                // Baca satu baris
                satuBaris = berkasBuffered.readLine();
                while (satuBaris != null) { // Selagi masih ada baris yang masih bisa dibaca
                    // Periksa apakah ini baris pertama atau tidak.
                    // Jika baris pertama maka tidak ada \n sebelumnya
                    // jika bukan baris pertama maka ada \n untuk memisahkannya dengan baris
                    // sebelumnya
                    if (barisPertama == false)
                        stringWriter.write("\n");
                    else
                        barisPertama = false;

                    // Serahkan baris yang baru dibaca ke StringWriter agar dapat disambung dengan
                    // baris-baris yang telah dibaca sebelumnya.
                    stringWriter.write(satuBaris);

                    // Baca lagi satu baris
                    satuBaris = berkasBuffered.readLine();
                }
            }
            catch (IOException salah) {
                salah.printStackTrace();
            }
            finally {
                // Jangan lupa ditutup
                berkasBuffered.close();
            }
        }
        catch (FileNotFoundException salah) {
            salah.printStackTrace();
        }
        catch (IOException salah) {
            salah.printStackTrace();
        }

        // Kembalikan isi berkas
        return stringWriter.toString();
    }

    /**
     * Menulis ke suatu berkas text.
     *
     * @param isi Apa yang harus ditulis ke berkas.
     */
    private void simpanBerkas(String isi) {
        FileOutputStream berkasStream = null;
        try {
            // Buka berkas untuk ditulis, pakai buffer biar lebih efisien
            berkasStream = openFileOutput(NAMA_BERKAS, MODE_PRIVATE);
            OutputStreamWriter berkasStreamWriter = new OutputStreamWriter(berkasStream);
            BufferedWriter berkasBuffered = new BufferedWriter(berkasStreamWriter);

            try {
                // Tulis ke berkas
                berkasBuffered.write(isi);
            }
            catch (IOException salah) {
                salah.printStackTrace();
            }
            finally {
                // Jangan lupa ditutup
                berkasBuffered.close();
            }
        }
        catch (FileNotFoundException salah) {
            salah.printStackTrace();
        }
        catch (IOException salah) {
            salah.printStackTrace();
        }
    }
}
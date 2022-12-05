package com.example.proyecto_final.ui.product;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.proyecto_final.R;
import com.example.proyecto_final.databinding.FragmentNewProductBinding;
import com.example.proyecto_final.databinding.FragmentProductDetailBinding;
import com.example.proyecto_final.entities.Category;
import com.example.proyecto_final.entities.Product;
import com.example.proyecto_final.ui.category.CategoryViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class NewProductFragment extends Fragment {

    private FragmentNewProductBinding binding;
    private Uri image;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_GALLERY = 2;
    public static final int REQUEST_FILE = 3;
    public static final int REQUEST_IMAGE_PERM = 101;
    private ProductsViewModel mViewModel;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                List<String> categoryNames = new ArrayList<>();
                categoryNames.add("Select a category");
                categoryNames.addAll(categories.stream()
                        .map(Category::getName)
                        .collect(Collectors.toList()));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categoryNames);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                binding.newProductCategorySpinner.setAdapter(arrayAdapter);
            }
        });

        binding.newProductSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productDescription = binding.newProductDescription.getText().toString();
                if (productDescription.equals("")){
                    binding.newProductDescription.setError("Este campo no puede estar vacío");
                    return;
                }

                String productPrice = binding.newProductPrice.getText().toString();
                if(productPrice.equals("")){
                    binding.newProductPrice.setError("Este campo no puede estar vacío");
                    return;
                }
                Double precio = Double.parseDouble(productPrice);
                if(precio <= 0){
                    binding.newProductPrice.setError("Este valor es inválido");
                    return;
                }

                int productCategoryPos = binding.newProductCategorySpinner.getSelectedItemPosition();

                if(productCategoryPos == 0){
                    Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_LONG).show();
                    return;
                }
                String productCategory = binding.newProductCategorySpinner.getItemAtPosition(productCategoryPos).toString();

                Product product = new Product(productDescription, precio, productCategory);
                if(image != null){
                    uploadToFirebase(image, product);
                }else{
                    mViewModel.insert(product);
                    clearInputs();
                    getActivity().onBackPressed();
                }
            }
        });

        binding.newProductClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputs();
            }
        });

        binding.newProductImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPictureDialog();
            }
        });


    }

    private void clearInputs(){
        binding.newProductImgView.setImageResource(R.drawable.ic_image_not_found);
        binding.newProductDescription.setText("");
        binding.newProductPrice.setText("");
        binding.newProductCategorySpinner.setSelection(0);
        image = null;
    }

    private void uploadToFirebase(Uri imageUri, Product newProduct) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(imageUri);

        StorageReference fileRef = mStorageRef.child("Images/Products/" + newProduct.getProductID() + "/" + imageFileName);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("item", "getDownloadUrl: " + uri.toString());
//                        newProduct.setImgUrl(uri.toString());
                        mViewModel.insert(newProduct);
                        clearInputs();
                        getActivity().onBackPressed();
                    }
                });
                Toast.makeText(getContext(), "Image uploaded succesfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image uploading Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private void startPictureDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose your category picture");
        builder.setItems(new CharSequence[]
                        {"Take photo", "Choose from gallery", "Choose from file", "Cancel"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                dispatchTakePictureIntent();
                                break;
                            case 1:
                                dispatchTakeFromGalleryIntent();
                                break;
                            case 2:
                                dispatchTakeFromFilesIntent();
                                break;
                            case 3:
                                dialog.cancel();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        System.out.println(getActivity().getPackageManager());
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                image = FileProvider.getUriForFile(getContext(),
                        "com.example.proyecto_final.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void dispatchTakeFromGalleryIntent(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_GALLERY);

    }

    private void dispatchTakeFromFilesIntent(){
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("image/*");
        startActivityForResult(fileIntent, REQUEST_FILE);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Save a file: path for use with ACTION_VIEW intents

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            binding.newProductImgView.setImageURI(image);
        }

        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null){
            image = data.getData();
            binding.newProductImgView.setImageURI(image);
        }

        if(requestCode == REQUEST_FILE && resultCode == RESULT_OK && data != null){
            image = data.getData();
            binding.newProductImgView.setImageURI(image);
        }
    }
}
package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 = "select og.* from ogrenci as og\n" +
            "inner join islem as isl\n" +
            "on og.ogrno=isl.ogrno";

    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = "select * from ogrenci\n" +
            "where ogrno not in (select ogrno from islem)";
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = "select o.sinif, count(i.ogrno) from ogrenci as o\n" +
            "inner join islem as i\n" +
            "on o.ogrno=i.ogrno\n" +
            "where sinif='10A' or sinif='10B'\n" +
            "group by o.sinif";
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = "select count(ogrno) as ogrenciSayisi from  ogrenci ";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = "select count(Distinct ad) from ogrenci";
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = "select og.ad,count(ad) from ogrenci as og\n" +
            "group by og.ad";
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = "select sinif,count(sinif) from ogrenci where sinif in\n" +
            "( select distinct sinif from ogrenci )group by sinif";
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = "\n" +
            "select og.ad,og.soyad,count(isl.kitapno) from ogrenci as og\n" +
            "inner join islem isl\n" +
            "on og.ogrno=isl.ogrno\n" +
            "group by og.ad,og.soyad";
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}

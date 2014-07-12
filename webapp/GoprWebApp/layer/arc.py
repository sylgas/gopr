import arcpy
import os

arcpy.env.workspace = r"C:\Users\Ucash\Documents\gopr\webapp\GoprWebApp\layer"
arcpy.FeaturesToJSON_conversion("Areas.lyr","AreasFormated.json","FORMATTED")

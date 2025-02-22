import { Controller, Get, Query } from '@nestjs/common';
import { LocalidadeService } from './localidade.service';
import { ApiQuery, ApiResponse } from '@nestjs/swagger';
import { VehicleType } from './model/vehicle-type.enum';

@Controller('localidade')
export class LocalidadeController {

    constructor(private readonly service: LocalidadeService) {}

    @Get()
    @ApiResponse({ status: 200, description: 'Data retrieved successfully.' })
    @ApiResponse({ status: 404, description: 'Data not found.' })
    @ApiQuery({ name: 'lat', required: true, description: 'Expected latitude.' })
    @ApiQuery({ name: 'lon', required: true, description: 'Expected longitude.' })
    @ApiQuery({ name: 'localLat', required: true, description: 'Expected latitude destin.' })
    @ApiQuery({ name: 'localLon', required: true, description: 'Expected longitude destin.' })
    @ApiQuery({
        name: 'veiculo',
        required: false,
        description: 'Vehicle types enum.',
        enum: VehicleType
    })
    async getData(
        @Query('lat') lat: string,
        @Query('lon') lon: string,
        @Query('localLat') localLat: string,
        @Query('localLon') localLon: string,
        @Query('veiculo') veiculo: VehicleType
    ) {
        return this.service.getExpectLocale(lat, lon, localLat, localLon, veiculo);
    }

    @Get("station")
    @ApiResponse({ status: 200, description: 'Data retrieved successfully.' })
    @ApiResponse({ status: 404, description: 'Data not found.' })
    @ApiQuery({ name: 'type', required: true, description: 'Localization type.' })
    @ApiQuery({ name: 'localLat', required: true, description: 'Expected latitude destin.' })
    @ApiQuery({ name: 'localLon', required: true, description: 'Expected longitude destin.' })
    async getStation(
        @Query('type') type: string,
        @Query('localLat') localLat: string,
        @Query('localLon') localLon: string,
    ) {
        return this.service.getStation(type, localLat, localLon);
    }

    @Get('search')
    @ApiResponse({ status: 200, description: 'Places retrieved successfully.' })
    @ApiResponse({ status: 404, description: 'Places not found.' })
    @ApiQuery({ name: 'location', required: true, description: 'Name of the location to search for.' })
    @ApiQuery({ name: 'lat', required: true, description: 'Expected latitude origin.' })
    @ApiQuery({ name: 'lon', required: true, description: 'Expected longitude origin.' })
    async searchPlaces(
        @Query('location') location: string,
        @Query('lat') lat: string,
        @Query('lon') lon: string
    ) {
      return this.service.searchPlaces(location, lat, lon);
    }
}
